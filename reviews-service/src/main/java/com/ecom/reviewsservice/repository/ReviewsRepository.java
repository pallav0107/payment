package com.ecom.reviewsservice.repository;

import com.ecom.reviewsservice.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}
