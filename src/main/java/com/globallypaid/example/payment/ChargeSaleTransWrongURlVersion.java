package com.globallypaid.example.payment;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.BasicInterface;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.service.GloballyPaid;
import com.globallypaid.service.PaymentInstrument;
import com.globallypaid.util.JsonUtils;
import java.io.IOException;
import java.util.Map;

import static com.globallypaid.util.Constants.VERSION;

public class ChargeSaleTransWrongURlVersion {
  public static void main(String[] args) throws IOException, GloballyPaidException {
    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .type("creditcard")
            .creditCard(MockModel.getCreditCard())
            .billingContact(MockModel.getBillingContact())
            .build();

    TokenRequest tokenRequest = TokenRequest.builder().paymentInstrument(paymentInstrument).build();

    // set wrong API version
    BasicInterface.setVersion(VERSION.concat("3"));

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(30 * 1000).build();
      paymentInstrumentToken = globallyPaid.token(tokenRequest, requestOptions);
    } catch (GloballyPaidException e) {
      System.out.println(
          "*** Tokenization Error *** \nCode: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());

      if (!e.getGloballyPaidError().isEmpty()) {
        Map<String, Object> apiError =
            JsonUtils.convertFromJsonToObject(
                JsonUtils.convertFromObjectToJson(e.getGloballyPaidError().get("error")),
                Map.class);
        System.out.println(
            "\n*** Tokenization Api Error *** \nCode: "
                + apiError.get("code")
                + ", Msg: "
                + apiError.get("message"));
      }
    }
    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

      try {
        ChargeResponse chargeResponse =
            globallyPaid.charge(
                MockModel.getChargeRequestWithClientInfo(paymentInstrumentToken.getId()));
        System.out.println(chargeResponse.toString());
      } catch (GloballyPaidException e) {
        System.out.println(
            "ChargeSaleTrans ---> Code: "
                + e.getCode()
                + "\nMsg: "
                + e.getMessage()
                + "\nApi error: "
                + e.getGloballyPaidError());
      }
    }
  }
}
