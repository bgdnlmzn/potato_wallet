package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.*;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.mapper.UserMapper;
import ru.cft.template.repository.SessionRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final WalletService walletService;
    private final SessionRepository sessionRepository;

    @Transactional
    public TokenDto createUser(UserCreateRequest body) {
        User user = UserMapper.mapRegBody(body);
        log.info(user.toString());
        isUserCreated(user);
        Wallet wallet = walletService.createWallet();

        user.setWallet(wallet);
        userRepository.save(user);

        String token = createSessionForUser(user);

        return TokenDto.builder().token(token).build();
    }
    public String createSessionForUser(User user){
        String token = jwtTokenUtils.generateToken(user);
        Date expiryDate = jwtTokenUtils.getDateFromToken(token);

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setExpirationTime(expiryDate);
        sessionRepository.save(session);
        return token;
    }
    private void isUserCreated(User user){
        if (user.getFirstName() == null) {
            throw new UsernameNotFoundException("Пользватель не создан");
        }
    }

    public UserDto getUserResponse(Authentication authentication){
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return UserMapper.mapUserToResponse(user);
    }

    public User getUserByAuthentication(Authentication authentication){
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDto updateUser(UserUpdateRequest body,Authentication authentication) {
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        if (body.firstName() != null)
            user.setFirstName(body.firstName());
        if (body.lastName() != null)
            user.setLastName(body.lastName());
        if (body.middleName() != null)
            user.setMiddleName(body.middleName());
        if(body.birthdate() != null)
            user.setBirthDate(body.birthdate());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return UserMapper.mapUserToResponse(user);
    }

    public UserShortInfo findUserShortInfo(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return UserMapper.mapUserShortInfoBody(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
