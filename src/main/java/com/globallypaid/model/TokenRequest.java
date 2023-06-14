package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.model.common.PaymentInstrumentCard;
import com.globallypaid.service.PaymentInstrument;
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
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequest extends Entity {
  @JsonProperty("payment_instrument")
  private PaymentInstrumentCard PaymentInstrumentRequest;

  @JsonProperty("kount_session_id")
  private String kountSessionId;

  @JsonProperty(value = "cavv")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CAVV;

  @JsonProperty(value = "eciflag")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ECIFlag;

  @JsonProperty(value = "xid")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String XID;

  @JsonProperty(value = "requires_3ds")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean Requires3DS = false;

  // Change to object later
  @JsonProperty(value = "order")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Order;

  @JsonProperty(value = "jwt")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Jwt;
}
