package com.example.footmark.emoji.domain.repository;

import com.example.footmark.emoji.domain.Emoji;
import com.example.footmark.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EmojiRepository extends JpaRepository<Emoji, Long> {

    boolean existsByCreateAtAndMember(LocalDate createAt, Member member);
}
