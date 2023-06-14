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
public class Address {
  @JsonProperty(value = "line_1")
  private String Line1;

  @JsonProperty(value = "line_2")
  private String Line2;

  @JsonProperty(value = "city")
  private String City;

  @JsonProperty(value = "state")
  private String State;

  @JsonProperty(value = "postal_code")
  private String PostalCode;

  @JsonProperty(value = "country_code")
  private String CountryCode;
}
