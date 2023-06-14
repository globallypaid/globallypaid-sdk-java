package com.globallypaid.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.model.BillingContact;
import com.globallypaid.model.ShippingContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMeta {

    @JsonProperty(value = "client_transaction_id")
    private String ClientTransactionID;

    @JsonProperty(value = "client_customer_id")
    private String ClientCustomerID;

    @JsonProperty(value = "client_transaction_description")
    private String ClientTransactionDescription;

    @JsonProperty(value = "client_invoice_id")
    private String ClientInvoiceID;

    @JsonProperty(value = "shipping_info")
    private ShippingContact ShippingContact;

}
