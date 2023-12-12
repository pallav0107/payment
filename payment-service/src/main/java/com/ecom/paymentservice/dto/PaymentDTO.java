package com.ecom.paymentservice.dto;

import com.ecom.paymentservice.constant.CardType;
import com.ecom.paymentservice.constant.PaymentStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {
  private long paymentId;
  private long orderId;
  private long productId;
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
  @SerializedName("cardDetails")
  private CardDetailsDTO cardDetails;

}
