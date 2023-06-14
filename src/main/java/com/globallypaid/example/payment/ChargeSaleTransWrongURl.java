package com.globallypaid.example.payment;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.BasicInterface;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.model.common.PaymentInstrumentCard;
import com.globallypaid.service.GloballyPaid;

import java.io.IOException;
import java.time.Instant;

public class ChargeSaleTransWrongURl {
  public static void main(String[] args) throws IOException, GloballyPaidException {
    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    PaymentInstrumentCard paymentInstrument =
        PaymentInstrumentCard.builder()
            .CreditCard(MockModel.getCreditCard())
            .BillingContact(MockModel.getBillingContact())
            .build();

    TokenRequest tokenRequest = TokenRequest.builder().PaymentInstrumentRequest(paymentInstrument).build();

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
