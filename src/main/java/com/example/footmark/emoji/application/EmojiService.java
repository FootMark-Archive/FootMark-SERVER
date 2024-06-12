package com.example.footmark.emoji.application;

import com.example.footmark.emoji.api.dto.req.EmojiReqDto;
import com.example.footmark.emoji.api.dto.req.EmojiUpdateReqDto;
import com.example.footmark.emoji.api.dto.res.EmojiResDto;
import com.example.footmark.emoji.domain.Emoji;
import com.example.footmark.emoji.domain.repository.EmojiRepository;
import com.example.footmark.emoji.exception.EmojiAlreadyExistsForDateException;
import com.example.footmark.emoji.exception.EmojiMemberMismatchException;
import com.example.footmark.emoji.exception.EmojiNotFoundException;
import com.example.footmark.emoji.exception.EmojiOrMemberNullException;
import com.example.footmark.member.domain.Member;
import com.example.footmark.review.domain.Review;
import com.example.footmark.review.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmojiService {

    private final EmojiRepository emojiRepository;

    @Transactional
    public EmojiResDto createEmoji(EmojiReqDto emojiReqDto, Member member){

        if (emojiRepository.existsByCreateAtAndMember(emojiReqDto.createAt(), member)) {
            throw new EmojiAlreadyExistsForDateException();
        }

        Emoji emoji = builderEmoji(emojiReqDto, member);

        Emoji saveEmoji = emojiRepository.save(emoji);

        return EmojiResDto.of(saveEmoji);
    }

    private Emoji builderEmoji(EmojiReqDto emojiReqDto, Member member){
        return Emoji.builder()
                .member(member)
                .createAt(emojiReqDto.createAt())
                .todayEmoji(emojiReqDto.todayEmoji())
                .build();
    }

    @Transactional
    public EmojiResDto updateEmoji(String createAt, EmojiUpdateReqDto emojiReqDto, Member member){
        LocalDate date = LocalDate.parse(createAt);
        Emoji emoji = emojiRepository.findByCreateAtAndMember(date, member)
                .orElseThrow(EmojiNotFoundException::new);
        validateEmojiMemberMatch(emoji, member);

        emoji.update(emojiReqDto);
        return EmojiResDto.of(emoji);
    }

    private void validateEmojiMemberMatch(Emoji emoji, Member member){
        if(emoji == null || member == null) {
            throw new EmojiOrMemberNullException();
        }

        if(!emoji.getMember().getMemberId().equals(member.getMemberId())) {
            throw new EmojiMemberMismatchException();
        }
    }

    @Transactional
    public void deleteEmoji(String createAt, Member member){
        LocalDate date = LocalDate.parse(createAt);
        Emoji emoji = emojiRepository.findByCreateAtAndMember(date, member)
                .orElseThrow(EmojiNotFoundException::new);

        validateEmojiMemberMatch(emoji, member);

        emojiRepository.delete(emoji);
    }
}
