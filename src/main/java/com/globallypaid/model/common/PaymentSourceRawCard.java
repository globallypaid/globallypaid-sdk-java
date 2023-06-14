package com.globallypaid.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import com.globallypaid.model.CreditCard;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Data
@AllArgsConstructor
@Jacksonized
@SuperBuilder
public class PaymentSourceRawCard extends PaymentSource {

    {
        Type = PaymentSourceType.CREDIT_CARD;
    }

    @JsonProperty(value = "credit_card")
    private CreditCard CreditCard;

}
