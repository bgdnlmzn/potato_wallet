package ru.cft.template.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.cft.template.entity.User;
import ru.cft.template.repository.BannedTokenRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final BannedTokenRepository bannedTokenRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (isTokenBanned(jwt, response)) return;

            processTokenAuthentication(jwt);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenBanned(String jwt, HttpServletResponse response) throws IOException {
        if (bannedTokenRepository.findByToken(jwt).isPresent()) {
            log.info("Attempt to use a banned token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Токен заблокирован");
            return true;
        }
        return false;
    }

    private void processTokenAuthentication(String jwt) {
        try {
            UUID userId = jwtTokenUtils.getUserId(jwt);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(jwt, userId);
            }
        } catch (ExpiredJwtException e) {
            log.debug("Токен просрочен");
        } catch (Exception e) {
            log.error("Ошибка обработки JWT", e);
        }
    }

    private void authenticateUser(String jwt, UUID userId) {
        userRepository.findById(userId).ifPresent(user -> {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user, jwt, Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        });
    }
}

