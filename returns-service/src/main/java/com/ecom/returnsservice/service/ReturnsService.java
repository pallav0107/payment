package com.ecom.returnsservice.service;


import com.ecom.ordermanagementservice.exception.BusinessException;

import com.ecom.returnsservice.constant.ErrorConstant;
import com.ecom.returnsservice.dto.ReturnsDTO;
import com.ecom.returnsservice.model.Returns;
import com.ecom.returnsservice.repository.ReturnsRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnsService {


  private static final Logger log = LoggerFactory.getLogger(ReturnsService.class);
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private ReturnsRepository returnsRepository;


  @Transactional
  public ReturnsDTO addReturns(@NonNull ReturnsDTO returnsDTO) {
    Returns returns = this.modelMapper.map(returnsDTO, Returns.class);
    Returns savedReturns = this.returnsRepository.save(returns);
    log.info("Inside Returns Service:: returns added successfully ");
    return this.modelMapper.map(savedReturns, ReturnsDTO.class);
  }

  @Transactional
  public void deleteReturns(long returnsId) {
    Returns returns = returnsRepository.findById(returnsId)
        .orElseThrow(() -> new RuntimeException("Returns not found with Id"));
    returns.setStatus(false);
    log.info("Inside Returns Service:: returns deleted successfully with returnsId {}", returnsId);
  }

  /**
   * Get Employee list
   *
   * @param pageable pageable
   * @return List<ReturnsDTO>
   */
  @Transactional
  public List<ReturnsDTO> getReturns(Pageable pageable) {
    log.info("Inside Returns Service:: get returns ");
    return returnsRepository.findAllByStatus(true, pageable).stream()
        .map(returns -> this.modelMapper.map(returns, ReturnsDTO.class))
        .collect(Collectors.toList());
  }


  /**
   * Get returns by id
   *
   * @param returnsId returnsId
   * @return ReturnsDTO
   */
  @Transactional
  public ReturnsDTO getReturnsById(long returnsId) {
    Returns returns = returnsRepository.findById(returnsId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.RETURNS_NOT_FOUND));
    return this.modelMapper.map(returns, ReturnsDTO.class);
  }
}
