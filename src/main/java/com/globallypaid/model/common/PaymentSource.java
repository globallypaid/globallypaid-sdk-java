package com.globallypaid.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.Interface.IPaymentSource;
import com.globallypaid.enums.PaymentSourceType;
import com.globallypaid.model.BillingContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@SuperBuilder
public class PaymentSource implements IPaymentSource {

    @JsonProperty(value = "type")
    protected PaymentSourceType Type;

    @JsonProperty(value = "billing_contact")
    protected BillingContact BillingContact;

}
