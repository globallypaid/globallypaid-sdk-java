package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.service.PaymentInstrument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeResponse extends Entity {
  private String id;

  @JsonProperty("response_code")
  private String responseCode;

  private String message;
  private String approved;
  private Integer amount;

  private String response;

  @JsonProperty("new_payment_instrument")
  private PaymentInstrument paymentInstrument;
}
