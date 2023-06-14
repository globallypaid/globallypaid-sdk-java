package com.globallypaid.model.JSModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallypaid.enums.PaymentSourceType;
import com.globallypaid.model.BillingContact;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseTokenResponse {

    @JsonProperty(value = "token")
    private String Id;

//    @JsonProperty(value = "type")
//    private PaymentSourceType Type;

    @JsonProperty(value = "brand")
    private String Brand;

    @JsonProperty(value = "bin")
    private String Bin;

    @JsonProperty("lastFour")
    private String LastFour;

    // Company setting if raw pan is returned from the jsSDK
    @JsonProperty(value = "pan")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String PAN;

    @JsonProperty(value = "expiration")
    private String Expiration;

    // card cvv (returned from the js SDK but not direct api calls)
    @JsonProperty(value = "cvv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CVV;

    @JsonProperty(value = "billingContact")
    private ClientBillingContact BillingContact;

    @JsonProperty(value = "shippingContact")
    private ClientAddress ShippingContact;

    @JsonProperty("customer_id")
    private String CustomerId;

    @JsonProperty(value = "is_default")
    private boolean IsDefault;

    @JsonProperty("client_customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientCustomerId;

    @JsonProperty("client_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientId;

    @JsonProperty("created")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Created;

    @JsonProperty("updated")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Updated;

    @JsonProperty(value = "kountSessionId")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String KountSessionId;

    @JsonProperty(value = "v1_legacy_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String V1LegacyToken;

    @JsonProperty(value = "tokenExToken")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String TokenExToken;

    // TODO update to object
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
    private String XID;

}
