package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.HesoyamResponse;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.mapper.WalletMapper;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.utils.JwtTokenUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
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
        newWallet.setAmount(100L);
        newWallet.setUpdatedAt(LocalDateTime.now());
        return walletRepository.save(newWallet);
    }

    @Override
    public WalletDto getWallet(Authentication authentication) {
        UUID id = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        Optional<Wallet> wallet = walletRepository.findById(id);
        return WalletMapper.mapWallet(wallet);
    }

    @Override
    public HesoyamResponse hesoyam(Authentication authentication) {
        UUID userId = jwtTokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        Wallet wallet = user.getWallet();
        LocalDateTime lastUpdatedAt = wallet.getUpdatedAt();

        if (lastUpdatedAt != null && lastUpdatedAt.plusMinutes(1).isAfter(LocalDateTime.now())) {
            return new HesoyamResponse("Вы можете использовать эту функцию только один раз в минуту.");
        }

        Random random = new Random();
        int chance = random.nextInt(100);

        if (chance < 25) {
            wallet.setAmount(wallet.getAmount() + 10);
            wallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(wallet);
            return new HesoyamResponse("На ваш кошелек было добавлено 10 единиц.");
        } else {
            wallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(wallet);
            return new HesoyamResponse("В этот раз вам не повезло.");
        }
    }
}

