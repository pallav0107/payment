package com.ecom.paymentservice.dto;

import com.stripe.model.*;
import lombok.*;

import java.util.List;
import java.util.Map;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntentDTO {

  Long amount;

  Long amountCapturable;

  PaymentIntent.AmountDetails amountDetails;

  Long amountReceived;
  ExpandableField<Application> application;
  Long applicationFeeAmount;
  PaymentIntent.AutomaticPaymentMethods automaticPaymentMethods;

  Long canceledAt;

  String cancellationReason;
  String captureMethod;
  String clientSecret;
  String confirmationMethod;
  Long created;
  String currency;
  ExpandableField<Customer> customer;
  String description;
  String id;
  ExpandableField<Invoice> invoice;

  StripeError lastPaymentError;

  ExpandableField<Charge> latestCharge;

  Boolean livemode;
  Map<String, String> metadata;
  PaymentIntent.NextAction nextAction;
  String object;
  ExpandableField<Account> onBehalfOf;
  ExpandableField<PaymentMethod> paymentMethod;

  PaymentIntent.PaymentMethodOptions paymentMethodOptions;
  List<String> paymentMethodTypes;
  PaymentIntent.Processing processing;
  String receiptEmail;
  ExpandableField<Review> review;
  String setupFutureUsage;
  ShippingDetails shipping;
  ExpandableField<PaymentSource> source;
  String statementDescriptor;
  String statementDescriptorSuffix;
  String status;
  PaymentIntent.TransferData transferData;
  String transferGroup;

}
