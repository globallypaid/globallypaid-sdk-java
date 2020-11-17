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
import java.io.IOException;
import java.time.Instant;

public class ChargeSaleTransWrongURl {
  public static void main(String[] args) throws IOException, GloballyPaidException {
    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .apiKey(System.getenv("GLOBALLYPAID_API_KEY"))
                .appIdKey(System.getenv("GLOBALLYPAID_APP_ID_KEY"))
                .sharedSecretApiKey(System.getenv("GLOBALLYPAID_SHARED_SECRET_API_KEY"))
                .sandbox(System.getenv("GLOBALLYPAID_USE_SANDBOX"))
                .build());

    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .type("creditcard")
            .creditCard(MockModel.getCreditCard())
            .billingContact(MockModel.getBillingContact())
            .build();

    TokenRequest tokenRequest = TokenRequest.builder().paymentInstrument(paymentInstrument).build();

    // set wrong URI
    BasicInterface.setSandbox(false);

    PaymentInstrumentToken paymentInstrumentToken = null;
    System.out.println("*** Tokenization Start time: " + Instant.now() + " ***");
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
    }
    System.out.println("*** Tokenization End time: " + Instant.now() + " ***");

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
