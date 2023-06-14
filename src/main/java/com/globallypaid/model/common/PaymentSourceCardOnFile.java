package com.globallypaid.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@SuperBuilder
public class PaymentSourceCardOnFile extends PaymentSource{

    {
        Type = PaymentSourceType.CARD_ON_FILE;
    }

    @JsonProperty(value = "card_on_file", required = true)
    private CardOnFile CardOnFile;
}
