package com.globallypaid.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentRequest extends Entity{

    /**
     *  Token ID number (need a token to make a payment instrument here)
     */
    @JsonProperty(value = "source")
    private String Token;

    @JsonProperty(value = "client_customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientCustomerId;

    @JsonProperty(value = "processor_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ProcessorID;

}
