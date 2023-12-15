package com.ecom.customersupportservice.repository;

import com.ecom.customersupportservice.model.CustomerSupport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {
  List<CustomerSupport> findAllByStatus(boolean b, Pageable pageable);
}
