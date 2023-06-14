package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CaptureResponse extends Entity {
  @JsonProperty("charge_transaction_id")
  private String ChargeTransactionID;

  private Integer Amount;

  @JsonProperty("recurring")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean isRecurring;

  @JsonProperty(value = "id")
  private String Id;

  @JsonProperty("response_code")
  private String ResponseCode;

  @JsonProperty(value = "message")
  private String Message;

  //This is ignored when deepstack sends the response
//  private String Response;

  @JsonProperty(value = "approved")
  private boolean Approved;

  @JsonProperty("auth_code")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AuthCode;

  @JsonProperty("cvv_response")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CvvResponse;

  @JsonProperty("avs_response")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AvsResponse;

  @JsonProperty(value = "processor_transaction_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ProcessorTransactionID;

  @JsonProperty(value = "completed")
  private String Completed;
}
