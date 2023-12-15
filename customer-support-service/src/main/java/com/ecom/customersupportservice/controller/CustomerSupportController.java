package com.ecom.customersupportservice.controller;

import com.ecom.customersupportservice.dto.CustomerSupportDTO;
import com.ecom.customersupportservice.service.CustomerSupportService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/support")
@AllArgsConstructor
public class CustomerSupportController {
  private static final Logger log = LoggerFactory.getLogger(CustomerSupportController.class);
  @Autowired
  private CustomerSupportService customerSupportService;

  @GetMapping(value = "/{interactionId}")
  public ResponseEntity<CustomerSupportDTO> getCustomerSupportById(@PathVariable long interactionId) {
    return new ResponseEntity<>(customerSupportService.getCustomerSupportById(interactionId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CustomerSupportDTO> addCustomerSupport(
      @RequestBody @NonNull final CustomerSupportDTO customerSupportDTO) {
    log.info("Inside CustomerSupport controller:: add new CustomerSupport");
    return new ResponseEntity<>(customerSupportService.addCustomerSupport(customerSupportDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{paymentId}")
  public ResponseEntity<String> deleteCustomerSupport(@PathVariable long paymentId) {
    log.info("Inside CustomerSupport controller:: delete CustomerSupport");
    customerSupportService.deleteCustomerSupport(paymentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CustomerSupportDTO>> getAllCustomerSupports(@RequestParam int page,
      @RequestParam int size) {
    log.info("Inside CustomerSupport controller:: get all CustomerSupports");
    return new ResponseEntity<>(customerSupportService.getCustomerSupport(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

}
