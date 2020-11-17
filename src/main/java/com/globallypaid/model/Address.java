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
  @JsonProperty("line_1")
  private String line1;

  @JsonProperty("line_2")
  private String line2;

  private String city;
  private String state;

  @JsonProperty("postal_code")
  private String postalCode;

  private String country;
}
