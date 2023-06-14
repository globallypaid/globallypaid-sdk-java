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
public class BillingContact {
  @JsonProperty(value = "first_name")
  private String FirstName;

  @JsonProperty(value = "last_name")
  private String LastName;

  @JsonProperty(value = "address")
  private Address Address;

  @JsonProperty(value = "phone")
  private String Phone;

  @JsonProperty(value = "email")
  private String Email;
}
