package com.ecom.paymentservice.controller;

import com.ecom.paymentservice.dto.PaymentDTO;
import com.ecom.paymentservice.dto.PaymentIntentDTO;
import com.ecom.paymentservice.exception.BusinessException;
import com.ecom.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.config.ConfigException;
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
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {


  private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
  @Autowired
  private PaymentService paymentService;

  @GetMapping(value = "/{paymentId}")
  public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable long paymentId) {
    return new ResponseEntity<>(paymentService.getPaymentById(paymentId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PaymentDTO> initiatePayment(
      @RequestBody @NonNull final PaymentDTO paymentDTO) {
    log.info("Inside Payment controller:: add new Payment");
    return new ResponseEntity<>(paymentService.initiatePayment(paymentDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{paymentId}")
  public ResponseEntity<String> refundPayment(@PathVariable long paymentId) {
    log.info("Inside Payment controller:: delete Payment");
    paymentService.refundPayment(paymentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<PaymentDTO>> getAllPayments(@RequestParam int page,
      @RequestParam int size) {
    log.info("Inside Payment controller:: get all Payments");
    return new ResponseEntity<>(paymentService.getPayments(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

  @PostMapping("/create-payment-intent")
  public ResponseEntity<PaymentIntentDTO> createPaymentIntent(
      @NonNull @RequestBody PaymentDTO paymentDTO) {
    try {
      log.info("Inside Payment controller:: create Payment Intent");
      PaymentIntentDTO paymentIntent = paymentService.createPaymentIntent(paymentDTO);
      return new ResponseEntity<>(paymentIntent, HttpStatus.CREATED);
    } catch (StripeException | IllegalAccessException e) {
      throw new BusinessException("Error in creating payment: " + e.getMessage());
    }
  }

  @PostMapping("/confirm-payment")
  public ResponseEntity<PaymentDTO> confirmPayment(@NonNull @RequestBody PaymentDTO paymentDTO) {
    try {
      log.info("Inside Payment controller:: confirm Payment");
      PaymentDTO paymentIntent = paymentService.confirmPayment(paymentDTO);
      return new ResponseEntity<>(paymentIntent, HttpStatus.ACCEPTED);
    } catch (StripeException | ConfigException e) {
      throw new BusinessException("Error in confirm payment: " + e.getMessage());
    }
  }


}
