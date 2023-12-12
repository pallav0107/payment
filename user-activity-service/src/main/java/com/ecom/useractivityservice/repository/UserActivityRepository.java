package com.ecom.useractivityservice.repository;

import com.ecom.useractivityservice.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
}
