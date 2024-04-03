package com.example.footmark.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long memberId, String username, String password, String nickname, Role role) {
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}