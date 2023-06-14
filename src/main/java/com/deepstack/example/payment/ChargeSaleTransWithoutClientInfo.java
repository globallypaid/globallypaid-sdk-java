package com.deepstack.example.payment;

import com.deepstack.example.MockModel;
import com.deepstack.exception.GloballyPaidException;
import com.deepstack.http.Config;
import com.deepstack.http.RequestOptions;
import com.deepstack.model.ChargeResponse;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.TokenRequest;
import com.deepstack.service.DeepStack;

import java.io.IOException;

public class ChargeSaleTransWithoutClientInfo {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    DeepStack deepStack =
        new DeepStack(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      TokenRequest tokenRequest =
          TokenRequest.builder().PaymentInstrumentRequest(MockModel.getPaymentInstrumentCard()).build();
      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(50 * 1000).build();
      paymentInstrumentToken = deepStack.token(tokenRequest, requestOptions);
    } catch (GloballyPaidException e) {
      System.out.println(
          "Tokenization ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

      try {
        ChargeResponse chargeResponse =
            deepStack.charge(
                MockModel.getChargeRequestWithoutClientInfo(paymentInstrumentToken));
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
