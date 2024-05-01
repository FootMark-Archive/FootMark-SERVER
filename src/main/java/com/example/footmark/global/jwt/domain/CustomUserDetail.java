package com.example.footmark.global.jwt.domain;

import com.example.footmark.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor
@Getter
public class CustomUserDetail implements UserDetails {

    private final Member member;

    public CustomUserDetail customUserDetail(Member member) {
        return new CustomUserDetail(member);
    }

    // 해당 유저의 권한을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Member의 Role을 GrantedAuthority로 변환
        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());
        return Collections.singletonList(authority); // 단일 권한을 반환
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
