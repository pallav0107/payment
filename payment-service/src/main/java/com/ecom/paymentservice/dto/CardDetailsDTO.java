package com.ecom.paymentservice.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsDTO {
  @SerializedName("cvc")
  String cvc;
  @SerializedName("exp_month")
  Long expMonth;
  @SerializedName("exp_year")
  Long expYear;
  @SerializedName("_stripe_java_extra_param_key")
  Map<String, Object> extraParams;
  @SerializedName("number")
  String number;
}
