package com.globallypaid.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.CofType;
import com.globallypaid.model.Entity;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TransactionParameters extends Entity {

    @JsonProperty(value = "amount", required = true)
    private Integer Amount;

    @Builder.Default
    @JsonProperty(value="capture", required = true)
    private boolean Capture = true;

    @Builder.Default
    @JsonProperty(value="cof_type")
    private CofType cofType = CofType.UNSCHEDULED_CARDHOLDER;

    @JsonProperty(value="country_code")
    private String CountryCode;

    @JsonProperty(value="currency_code")
    private String CurrencyCode;

    @Builder.Default
    @JsonProperty(value="avs")
    private boolean AVS = true;

    @Builder.Default
    @JsonProperty(value = "save_payment_instrument")
    private boolean SavePaymentInstrument = false;

    @JsonProperty(value = "kount_session_id")
    private String KountSessionId;
}
