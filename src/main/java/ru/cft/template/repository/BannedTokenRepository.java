package ru.cft.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.BannedToken;

import java.util.Optional;

@Repository
public interface BannedTokenRepository extends JpaRepository<BannedToken,String> {
    Optional<BannedToken> findByToken(String token);
}
