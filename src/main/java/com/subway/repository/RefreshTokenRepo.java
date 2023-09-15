package com.subway.repository;

import com.subway.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

    Boolean existsByUserId(String userId);

    void deleteByRefreshToken(String refreshToken);
    void deleteByUserId(String userId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
