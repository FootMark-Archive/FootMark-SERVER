package com.example.footmark.global.jwt.domain;

import com.example.footmark.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId; // Token 엔티티의 기본 키

    @Column(nullable = false)
    private String refreshToken; // 토큰 값

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(nullable = false)
    private Date expiredDt; // 만료 시간

    @Builder
    public Token(String refreshToken, Member member, Date expiredDt) {
        this.refreshToken = refreshToken;
        this.member = member;
        this.expiredDt = expiredDt;
    }

    public void updateToken(String refreshToken, Date refreshTokenExpiryDate) {
            if (!this.refreshToken.equals(refreshToken)) {
                this.refreshToken = refreshToken;
            }
    }

}
