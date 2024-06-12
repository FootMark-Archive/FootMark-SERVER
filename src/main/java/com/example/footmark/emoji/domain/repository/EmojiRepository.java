package com.example.footmark.emoji.domain.repository;

import com.example.footmark.emoji.domain.Emoji;
import com.example.footmark.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmojiRepository extends JpaRepository<Emoji, Long> {

    boolean existsByCreateAtAndMember(LocalDate createAt, Member member);

    Optional<Emoji> findByCreateAtAndMember(LocalDate date, Member member);
}
