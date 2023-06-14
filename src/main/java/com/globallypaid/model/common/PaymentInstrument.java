package com.globallypaid.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class PaymentInstrument {

    @JsonProperty(value = "id")
    protected String Id;
    @JsonProperty(value = "type")
    protected PaymentSourceType Type;

}
