package com.example.footmark.global.jwt.api.dto.req;

import com.example.footmark.member.domain.Member;
import com.example.footmark.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpReqDto {

    private String username;
    private String password;
    private String nickname;

    private Role role;
    public Member toEntity(String encodedPassword) {

        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .role(Role.ROLE_USER)
                .build();
    }
}
