package ru.cft.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByMobilePhone(String mobilePhone);
    //Optional<User> findByEmail(String email);
    Optional<User> findByWallet(Wallet wallet);
}
