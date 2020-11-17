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
public class CaptureResponse extends Entity {
  @JsonProperty("charge_transaction_id")
  private String chargeTransactionID;

  private Integer amount;

  @JsonProperty("recurring")
  private boolean isRecurring;

  private String id;

  @JsonProperty("response_code")
  private String responseCode;

  private String message;
  private String response;
  private boolean approved;

  @JsonProperty("auth_code")
  private String authCode;

  @JsonProperty("cvv_response")
  private String cvvResponse;

  @JsonProperty("avs_response")
  private String avsResponse;

  @JsonProperty("processor_transaction_id")
  private String processorTransactionID;

  private String completed;
}
