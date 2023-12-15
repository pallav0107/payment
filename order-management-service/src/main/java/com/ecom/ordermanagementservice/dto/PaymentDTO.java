package com.ecom.ordermanagementservice.dto;

import com.ecom.ordermanagementservice.constant.CardType;
import com.ecom.ordermanagementservice.constant.PaymentStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {
  String cvc;
  Long expMonth;
  Long expYear;
  String number;
  private long paymentId;
  private long productId;
  private long orderId;
  private Long amount;
  private String currency;
  private PaymentStatus paymentStatus;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime timestamp;
  private String paymentGateway;
  private CardType cardType;
  private int cardLastDigits;
  private String billingAddress;
  private String transactionId;

}
