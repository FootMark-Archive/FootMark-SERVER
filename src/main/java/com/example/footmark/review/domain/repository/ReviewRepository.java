package com.example.footmark.review.domain.repository;

import com.example.footmark.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository{

}
