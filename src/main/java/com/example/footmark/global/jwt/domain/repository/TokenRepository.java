package com.example.footmark.global.jwt.domain.repository;

import com.example.footmark.global.jwt.domain.Token;
import com.example.footmark.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByMember(Member member);

    Optional<Token> findByRefreshToken(String refreshToken);

    boolean existsByRefreshToken(String refreshToken);

    boolean existsByMember(Member member);
}
