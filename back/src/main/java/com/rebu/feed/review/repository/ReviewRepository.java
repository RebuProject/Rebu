package com.rebu.feed.review.repository;

import com.rebu.feed.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByWriterId(Long writerId);
}