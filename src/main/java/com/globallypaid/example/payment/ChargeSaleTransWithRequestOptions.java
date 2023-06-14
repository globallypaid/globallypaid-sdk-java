package com.globallypaid.example.payment;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.service.GloballyPaid;
import java.io.IOException;

public class ChargeSaleTransWithRequestOptions {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    TokenRequest tokenRequest =
        TokenRequest.builder().PaymentInstrumentRequest(MockModel.getPaymentInstrumentCard()).build();

    try {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).publishableApiKey("skdjskjfs").build();
      globallyPaid.token(tokenRequest, requestOptions);
    } catch (GloballyPaidException e) {
      System.out.println(
          "Tokenization ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }

    try {
      ChargeResponse chargeResponse =
          globallyPaid.charge(
              MockModel.getChargeRequestWithClientInfo(globallyPaid.token(tokenRequest).getId()));
      System.out.println("chargeResponse: " + chargeResponse);
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
