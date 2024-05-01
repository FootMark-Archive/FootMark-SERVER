package com.example.footmark.member.domain.repository;

import com.example.footmark.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT MAX(m.memberId) FROM Member m")
    Optional<Long> findMaxId();
}
