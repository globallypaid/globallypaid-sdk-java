package com.globallypaid.model;

import com.fasterxml.jackson.annotation.*;
import com.globallypaid.enums.CofType;
import com.globallypaid.model.common.*;
import com.globallypaid.service.PaymentInstrument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeResponse extends Entity {

//  Transaction Identifier
  @JsonProperty(value = "id")
  private String ID;

  @JsonProperty("response_code")
  private String ResponseCode;

  @JsonProperty(value = "message")
  private String Message;

  @JsonProperty(value = "approved")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean approved;

  @JsonProperty(value = "auth_code")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AuthCode;

  @JsonProperty(value = "cvv_result")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CVVResponse;

  @JsonProperty(value = "avs_result")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AVSResponse;

  @JsonProperty(value = "source")
  // need a custom json deserializer here...
  // Actually nvm we always return a PaymentSourceCard
  private PaymentInstrumentCard Source;

  @JsonProperty(value = "amount")
  private Integer Amount;

  @JsonProperty(value = "captured")
  private Boolean Captured;

  @JsonProperty(value = "cof_type")
  private CofType CofType;

  @JsonProperty(value = "currency_code")
  private String CurrencyCode;

  @JsonProperty(value = "country_code")
  private String CountryCode;

  @JsonProperty(value = "billing_info")
  private BillingContact BillingInfo;

  @JsonProperty(value = "save_payment_instrument")
  private boolean SavePaymentInstrument;

  @JsonProperty("new_payment_instrument")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private PaymentInstrumentCardOnFile paymentInstrument;

  @JsonProperty(value = "kount_score")
  private String KountScore;

  @JsonProperty(value = "client_transaction_id")
  private String ClientTransactionID;

  @JsonProperty(value = "client_customer_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ClientCustomerID;

  @JsonProperty(value = "client_transaction_description")
  private String ClientTransactionDescription;

  @JsonProperty(value = "client_invoice_id")
  private String ClientInvoiceID;

  @JsonProperty(value = "shipping_info")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ShippingContact ShippingContact;

  @JsonProperty(value = "checks")
  private Checks Checks;

  @JsonProperty(value = "completed")
  private String Completed;

}
