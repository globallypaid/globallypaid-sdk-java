package com.globallypaid.model.JSModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddress {

    @JsonProperty(value = "lineOne")
    private String Line1;

    @JsonProperty(value = "lineTwo")
    private String Line2;

    @JsonProperty(value = "city")
    private String City;

    @JsonProperty(value = "state")
    private String State;

    @JsonProperty(value = "postalCode")
    private String PostalCode;

    @JsonProperty(value = "country")
    private String CountryCode;

}
