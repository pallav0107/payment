package com.ecom.paymentservice.service;


import com.ecom.paymentservice.constant.ErrorConstant;
import com.ecom.paymentservice.constant.PaymentStatus;
import com.ecom.paymentservice.dto.PaymentDTO;
import com.ecom.paymentservice.dto.PaymentIntentDTO;
import com.ecom.paymentservice.exception.BusinessException;
import com.ecom.paymentservice.model.Payment;
import com.ecom.paymentservice.publisher.KafkaPublisher;
import com.ecom.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentService {

  private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

  @Autowired
  private PaymentRepository paymentRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private KafkaPublisher kafkaPublisher;
  @Value("${stripe.secret-key}")
  private String secretKey;
  @Value("${stripe.token}")
  private String token;


  public PaymentIntentDTO createPaymentIntent(PaymentDTO paymentDTO)
      throws IllegalAccessException, StripeException {
    Stripe.apiKey = secretKey;
    PaymentMethodCreateParams.CardDetails card = PaymentMethodCreateParams.CardDetails.builder()
        .setNumber(paymentDTO.getCardDetails().getNumber())
        .setExpMonth(paymentDTO.getCardDetails().getExpMonth())
        .setExpYear(paymentDTO.getCardDetails().getExpYear())
        .setCvc(paymentDTO.getCardDetails().getCvc()).build();

    PaymentMethodCreateParams.Token token =
        PaymentMethodCreateParams.Token.builder().setToken("tok_visa").build();
    Map<String, Object> paymentMethodParams = new HashMap<>();
    paymentMethodParams.put("token", token);

    PaymentMethodCreateParams paymentMethodCreateParams =
        PaymentMethodCreateParams.builder().setType(PaymentMethodCreateParams.Type.CARD)
            .setCard(card).setCard(token).build();

    PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodCreateParams);

    Map<String, Object> params = new HashMap<>();
    params.put("amount", paymentDTO.getAmount());
    params.put("currency", paymentDTO.getCurrency());
    params.put("payment_method", paymentMethod.getId());

    Map<String, Object> automaticPaymentMethods = new HashMap<>();
    automaticPaymentMethods.put("enabled", true);
    automaticPaymentMethods.put("allow_redirects", "never");
    params.put("automatic_payment_methods", automaticPaymentMethods);

    PaymentIntent paymentIntent = PaymentIntent.create(params);
    // we can call direct confirm api
    //paymentIntent.confirm();
    PaymentIntentDTO paymentIntentDTO = convertToDTO(paymentIntent);
    return paymentIntentDTO;
  }



  private PaymentIntentDTO convertToDTO(PaymentIntent paymentIntent) {
    PaymentIntentDTO paymentIntentDTO = new PaymentIntentDTO();
    paymentIntentDTO.setId(paymentIntent.getId());
    paymentIntentDTO.setClientSecret(paymentIntent.getClientSecret());
    return paymentIntentDTO;
  }

  public PaymentDTO initiatePayment(PaymentDTO paymentDTO) {
    Payment payment = paymentRepository.save(modelMapper.map(paymentDTO, Payment.class));
    log.info("Inside Payment Service:: Payment added successfully ");
    return modelMapper.map(payment, PaymentDTO.class);
  }

  public void refundPayment(long paymentId) {
    paymentRepository.findById(paymentId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PAYMENT_NOT_FOUND));
    log.info("Inside Payment Service:: payment deleted successfully with {}", paymentId);
  }

  public List<PaymentDTO> getPayments(PageRequest pageRequest) {
    log.info("Inside Payment Service:: get payment");
    return paymentRepository.findAll(pageRequest).stream()
        .map(payment -> modelMapper.map(payment, PaymentDTO.class)).toList();
  }

  public PaymentDTO getPaymentById(long paymentId) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PAYMENT_NOT_FOUND));
    return modelMapper.map(payment, PaymentDTO.class);
  }

  public PaymentDTO confirmPayment(PaymentDTO paymentDTO) throws StripeException {
    Stripe.apiKey = secretKey;
    PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentDTO.getTransactionId());
    PaymentIntent result = paymentIntent.confirm(); // Confirm the payment
    if (result != null) {
      //save Payment into Payment DB
      paymentDTO.setPaymentStatus(PaymentStatus.valueOf(result.getStatus()));
      PaymentDTO responseDTO = this.initiatePayment(paymentDTO);

      // sending detail to kafka topic for confirm order

      kafkaPublisher.sendMessage(responseDTO);
      return responseDTO;
    }
    return paymentDTO;
  }

}
