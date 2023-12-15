package com.ecom.returnsservice.repository;

import com.ecom.returnsservice.model.Returns;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Long> {
  List<Returns> findAllByStatus(boolean b, Pageable pageable);
}
