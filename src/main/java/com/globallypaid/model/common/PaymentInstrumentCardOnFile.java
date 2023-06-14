package com.globallypaid.model.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import com.globallypaid.model.BillingContact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentCardOnFile extends PaymentInstrument{

    {
        Type = PaymentSourceType.CARD_ON_FILE;
    }

    @JsonProperty(value = "customer_id")
    private String CustomerId;

    @JsonProperty(value = "client_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientId;

    @JsonProperty(value = "client_customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientCustomerId;

    @JsonProperty(value = "brand")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Brand;

    @JsonProperty(value = "bin")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String BIN;

    @JsonProperty(value = "last_four")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String LastFour;

    @JsonProperty(value = "expiration")
    private String Expiration;

    @JsonProperty(value = "billing_contact")
    private BillingContact BillingContact;

    @JsonProperty(value = "is_default")
    private boolean IsDefault;

    @JsonProperty(value = "v1_legacy_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String V1LegacyToken;

    @JsonProperty(value = "token_ex_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String TokenExToken;

    // Need to flush out object here eventually but not now
    @JsonProperty(value = "cmpi_lookup_response")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CardinalMPI;

    @JsonProperty(value = "cavv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CAVV;

    @JsonProperty(value = "eciflag")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ECIFlag;

    @JsonProperty(value = "xid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Xid;


}
