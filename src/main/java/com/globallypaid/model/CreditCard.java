package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard {

  @JsonProperty(value = "account_number")
  private String Number;

  @JsonProperty(value = "expiration")
  private String Expiration;

  @JsonProperty(value = "cvv")
  private String CVV;

  @JsonProperty(value = "brand")
  private String Brand;

  @JsonProperty("last_four")
  private String LastFour;

  private String bin;
}
