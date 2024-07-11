package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.ActiveSessionDto;
import ru.cft.template.dto.DeleteSessionResponse;
import ru.cft.template.dto.LoginRequest;
import ru.cft.template.dto.SessionDto;
import ru.cft.template.entity.BannedToken;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;
import ru.cft.template.exception.AccessRightsException;
import ru.cft.template.exception.SessionNotFoundException;
import ru.cft.template.mapper.SessionMapper;
import ru.cft.template.repository.BannedTokenRepository;
import ru.cft.template.repository.SessionRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final BannedTokenRepository bannedTokenRepository;
    private final SessionMapper sessionMapper;

    @Override
    public SessionDto createSession(LoginRequest body) {
        User user = userRepository.findByMobilePhone(body.mobilePhone())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid login details"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid login details");
        }

        String token = jwtTokenUtils.generateToken(user);
        Date expiryDate = jwtTokenUtils.getDateFromToken(token);
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setExpirationTime(expiryDate);
        sessionRepository.save(session);

        return SessionMapper.mapSession(session);

    }


    @Override
    public ActiveSessionDto getSession(Authentication authentication) {
        String currentToken = (String) authentication.getCredentials();

        Session session = sessionRepository.findByToken(currentToken)
                .orElseThrow(() -> new SessionNotFoundException("Сессия не найдена"));

        Date now = new Date();
        boolean isTokenValid = jwtTokenUtils.getDateFromToken(currentToken).after(now);

        return SessionMapper.mapActiveSession(session, isTokenValid);

    }

    @Override
    public List<ActiveSessionDto> getActiveSessions(Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);

        List<Session> sessions = sessionRepository.findByUserId(user.getId());
        Date now = new Date();
        return sessions.stream()
                .map(session -> SessionMapper.mapActiveSession(session, session.getExpirationTime().after(now)))
                .collect(Collectors.toList());
    }

    @Override
    public DeleteSessionResponse deleteSession(Authentication authentication, String id) {
        UUID currentUserId = userService.getUserByAuthentication(authentication).getId();
        UUID sessionId = UUID.fromString(id);
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        validateSessionOwner(session, currentUserId);

        banToken(authentication);

        sessionRepository.deleteById(sessionId);

        return SessionMapper.mapDeleteSession("Удалено");
    }

    private void validateSessionOwner(Session session, UUID currentUserId) {
        if (!session.getUser().getId().equals(currentUserId)) {
            throw new AccessRightsException("You can only delete your own sessions");
        }
    }

    private void banToken(Authentication authentication) {
        BannedToken bannedToken = new BannedToken();
        bannedToken.setToken(authentication.getCredentials().toString());
        bannedTokenRepository.save(bannedToken);
    }
}
