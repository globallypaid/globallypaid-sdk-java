package com.globallypaid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  private String source;

  /**
   * The payment amount. A positive integer representing how much to charge in the smallest currency
   * unit (e.g. 100 cents to charge $1.00).
   */
  private Integer amount;

  @JsonProperty("client_customer_id")
  private String clientCustomerId;

  /**
   * By default set to true, and the charge will be immediately captured against the payment method
   * specified. When set to false, performs an authorization only and must be captured explicitly
   * later.
   */
  private Boolean capture;

  /**
   * Specifies whether the transaction is a recurring charge (eg: subscription, auto-ship, etc) or
   * not. Setting this property properly positively affects authorization rates.
   */
  private boolean recurring;

  /**
   * Three-letter <a href="https://www.iso.org/iso-4217-currency-codes.html">ISO currency code.
   * (e.g. USD)</a>
   */
  @JsonProperty("currency_code")
  private String currencyCode;

  /** A unique identifier for the transaction in the Company’s system. */
  @JsonProperty("client_transaction_id")
  private String clientTransactionId;

  /** A short description for this charge. */
  @JsonProperty("client_transaction_description")
  private String clientTransactionDescription;

  /** The Company’s invoice number/id for this charge */
  @JsonProperty("client_invoice_id")
  private String clientInvoiceId;

  /** The AVS setting should almost always be true. */
  private boolean avs;

  /** The user-agent string of the browser requesting the charge. Used for enhanced fraud checks. */
  @JsonProperty("user_agent")
  private String userAgent;

  /** The browser header. Used for enhanced fraud checks. */
  @JsonProperty("browser_header")
  private String browserHeader;

  /**
   * When <code>true</code>, GloballyPaid will save the card data for future use and return a
   * PaymentInstrument ID in the response.
   */
  @JsonProperty("save_payment_instrument")
  private boolean savePaymentInstrument;

  @JsonProperty("client_id")
  private String clientId;

  public void setCapture(Boolean capture) {
    this.capture = (capture == null || capture);
  }

  public Boolean getCapture() {
    return capture == null || capture;
  }

  public String getClientTransactionId() {
    return TextUtils.isBlank(clientTransactionId)
        ? DEFAULT_CLIENT_TRANSACTION_ID
        : clientTransactionId;
  }

  public String getClientTransactionDescription() {
    return TextUtils.isBlank(clientTransactionDescription)
        ? DEFAULT_CLIENT_TRANSACTION_DESC
        : clientTransactionDescription;
  }

  public String getClientInvoiceId() {
    return TextUtils.isBlank(clientInvoiceId) ? DEFAULT_CLIENT_INVOICE_ID : clientInvoiceId;
  }
}
