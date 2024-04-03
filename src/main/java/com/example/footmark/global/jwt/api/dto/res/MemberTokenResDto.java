package com.example.footmark.global.jwt.api.dto.res;

import com.example.footmark.member.domain.Member;


public record MemberTokenResDto(
        Long memberId, String username, String nickname
) {
    public static MemberTokenResDto from(Member member) {
        return new MemberTokenResDto(member.getMemberId(), member.getUsername(), member.getNickname());
    }
}
