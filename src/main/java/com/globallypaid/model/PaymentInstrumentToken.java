package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PaymentInstrumentToken extends Entity {
  private String id;
  private String type;
  private String brand;

  @JsonProperty("last_four")
  private String lastFour;

  private String expiration;

  @JsonProperty("billing_contact")
  private BillingContact billingContact;

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("client_customer_id")
  private String clientCustomerId;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("created")
  private String created;

  @JsonProperty("updated")
  private String updated;
}
