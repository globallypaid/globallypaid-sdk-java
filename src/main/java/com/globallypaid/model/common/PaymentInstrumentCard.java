package com.globallypaid.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import com.globallypaid.model.BillingContact;
import com.globallypaid.model.CreditCard;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentCard extends PaymentInstrument{

    {
        Type = PaymentSourceType.CREDIT_CARD;
    }

    @JsonProperty(value = "customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CustomerId;

    @JsonProperty(value = "client_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientId;

    @JsonProperty(value = "client_customer_id")
    private String ClientCustomerId;

    @JsonProperty(value = "billing_contact")
    private BillingContact BillingContact;

    @JsonProperty(value = "credit_card")
    private CreditCard CreditCard;

    @JsonProperty(value = "cavv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CAVV;

    @JsonProperty(value = "eciflag")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ECIFlag;

    @JsonProperty(value = "xid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String XID;

}
