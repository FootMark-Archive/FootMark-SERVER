package com.example.footmark.todo.domain.repository;

import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByMemberAndCategoryName(Member member, String categoryName);

    Optional<Category> findByCategoryIdAndMember(Long categoryId, Member member);
}
