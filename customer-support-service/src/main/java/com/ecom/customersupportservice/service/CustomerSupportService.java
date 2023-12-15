package com.ecom.customersupportservice.service;


import com.ecom.customersupportservice.constant.ErrorConstant;
import com.ecom.customersupportservice.dto.CustomerSupportDTO;
import com.ecom.customersupportservice.model.CustomerSupport;
import com.ecom.customersupportservice.repository.CustomerSupportRepository;
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
public class CustomerSupportService {

  private static final Logger log = LoggerFactory.getLogger(CustomerSupportService.class);
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private CustomerSupportRepository customerSupportRepository;


  @Transactional
  public CustomerSupportDTO addCustomerSupport(@NonNull CustomerSupportDTO customerSupportDTO) {
    CustomerSupport customerSupport = this.modelMapper.map(customerSupportDTO, CustomerSupport.class);
    CustomerSupport savedCustomerSupport = this.customerSupportRepository.save(customerSupport);
    log.info("Inside CustomerSupport Service:: customerSupport added successfully ");
    return this.modelMapper.map(savedCustomerSupport, CustomerSupportDTO.class);
  }

  @Transactional
  public void deleteCustomerSupport(long customerSupportId) {
    CustomerSupport customerSupport = customerSupportRepository.findById(customerSupportId)
        .orElseThrow(() -> new RuntimeException("CustomerSupport not found with Id"));
    customerSupport.setStatus(false);
    log.info("Inside CustomerSupport Service:: customerSupport deleted successfully with customerSupportId {}", customerSupportId);
  }

  /**
   * Get Employee list
   *
   * @param pageable pageable
   * @return List<CustomerSupportDTO>
   */
  @Transactional
  public List<CustomerSupportDTO> getCustomerSupport(Pageable pageable) {
    log.info("Inside CustomerSupport Service:: get customerSupport ");
    return customerSupportRepository.findAllByStatus(true, pageable).stream()
        .map(customerSupport -> this.modelMapper.map(customerSupport, CustomerSupportDTO.class))
        .collect(Collectors.toList());
  }


  /**
   * Get customerSupport by id
   *
   * @param customerSupportId customerSupportId
   * @return CustomerSupportDTO
   */
  @Transactional
  public CustomerSupportDTO getCustomerSupportById(long customerSupportId) {
    CustomerSupport customerSupport = customerSupportRepository.findById(customerSupportId)
        .orElseThrow(() -> new com.ecom.ordermanagementservice.exception.BusinessException(
            ErrorConstant.SUPPORT_NOT_FOUND));
    return this.modelMapper.map(customerSupport, CustomerSupportDTO.class);
  }
}
