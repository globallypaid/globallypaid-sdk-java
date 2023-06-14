package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.model.common.PaymentSource;
import com.globallypaid.model.common.PaymentSourceRawCard;
import com.globallypaid.model.common.TransactionMeta;
import com.globallypaid.model.common.TransactionParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.http.util.TextUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeRequest extends Entity {
  public static final String DEFAULT_CLIENT_TRANSACTION_ID = "000000000";
  public static final String DEFAULT_CLIENT_TRANSACTION_DESC = "No Description provided!";
  public static final String DEFAULT_CLIENT_INVOICE_ID = "000000";

  /** The ID of the payment method to be charged */
  @JsonProperty(value = "source", required = true)
  private PaymentSource Source;

  /**
   * The payment amount. A positive integer representing how much to charge in the smallest currency
   * unit (e.g. 100 cents to charge $1.00).
   */
  @JsonProperty(value = "transaction")
  private TransactionParameters Params;

  @JsonProperty(value = "meta")
  private TransactionMeta Meta;
}
