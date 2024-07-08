package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.mapper.WalletMapper;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public Wallet createWallet() {
        Wallet newWallet = new Wallet();
        newWallet.setAmount(0L);
        newWallet.setUpdatedAt(LocalDateTime.now());
        return walletRepository.save(newWallet);
    }

    @Override
    public WalletDto getWallet(Authentication authentication) {
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Пользователь не найден"));
        return WalletMapper.mapWallet(user.getWallet());
    }
}
