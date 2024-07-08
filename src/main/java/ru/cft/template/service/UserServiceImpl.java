package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.*;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.mapper.UserMapper;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final WalletService walletService;

    public TokenDto createUser(UserCreateRequest body) {
        User user = UserMapper.mapRegBody(body);
        Wallet wallet = walletService.createWallet();
        user.setWallet(wallet);
        userRepository.save(user);
        return TokenDto.builder().token(jwtTokenUtils.generateToken(user)).build();
    }

    public UserDto getUserResponse(Authentication authentication){
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return UserMapper.mapUserToResponse(user);
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
