package com.example.footmark.oauth.application;

import com.example.footmark.member.domain.Member;
import com.example.footmark.member.domain.Role;
import com.example.footmark.member.domain.SocialType;
import com.example.footmark.member.domain.repository.MemberRepository;

import com.example.footmark.oauth.api.dto.res.MemberLoginResDto;
import com.example.footmark.oauth.api.dto.res.UserInfo;
import com.example.footmark.oauth.exception.ExistsMemberEmailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.footmark.image.api.dto.res.DefaultImage.DEFAULT_IMAGE;

@Service
@Transactional(readOnly = true)
public class OAuthMemberService {
    private final MemberRepository memberRepository;

    public OAuthMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {

        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByUsername(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        String userPicture = getUserPicture(userInfo.picture());

        long maxId = memberRepository.findMaxId().orElse(0L) + 1;

        String nickname = userInfo.name() == null ? "FootMark-" + maxId : userInfo.name();

        return memberRepository.save(
                Member.builder()
                        .username(userInfo.email())
                        .nickname(nickname)
                        .password("social")
                        .picture(userPicture)
                        .socialType(provider)
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    private String getUserPicture(String picture) {
        return Optional.ofNullable(picture)
                .map(this::convertToHighRes)
                .orElse(DEFAULT_IMAGE.imageUrl);
    }

    private String convertToHighRes(String url){
        return url.replace("s96-c", "s2048-c");
    }

    private void validateSocialType(Member member, SocialType provider) {
        if (!provider.equals(member.getSocialType())) {
            throw new ExistsMemberEmailException();
        }
    }

}
