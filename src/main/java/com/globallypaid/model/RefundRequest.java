package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundRequest extends Entity {

  @JsonProperty(value = "charge")
  private String Charge;

  @JsonProperty(value = "amount")
  private Integer Amount;

  @JsonProperty(value = "reason")
  private String Reason;

  // Don't see it required on deepstack
//  private String reason;
//  private String id;
//  private String clientId;
}
