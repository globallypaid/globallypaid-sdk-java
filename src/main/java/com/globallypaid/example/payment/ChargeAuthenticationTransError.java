package com.globallypaid.example.payment;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.CaptureRequest;
import com.globallypaid.model.CaptureResponse;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.model.common.PaymentInstrumentCard;
import com.globallypaid.service.GloballyPaid;

import java.io.IOException;

public class ChargeAuthenticationTransError {
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

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      paymentInstrumentToken = globallyPaid.token(tokenRequest);
    } catch (GloballyPaidException e) {
      System.out.println(
          "Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

      // call charge with the following capture call
      ChargeResponse chargeResponse =
          globallyPaid.charge(
              MockModel.getChargeRequestWithCaptureFalse(paymentInstrumentToken.getId()));

      if (chargeResponse != null && !chargeResponse.getID().isEmpty()) {
        System.out.println(chargeResponse.toString());
        // call capture
        CaptureResponse captureResponse1 =
            globallyPaid.capture(
                CaptureRequest.builder()
                    .Charge(chargeResponse.getID())
                    .Amount(chargeResponse.getAmount())
                    .build());
        System.out.println("First capture call: " + captureResponse1);

        CaptureResponse captureResponse2 =
            globallyPaid.capture(
                CaptureRequest.builder()
                    .Charge(chargeResponse.getID())
                    .Amount(chargeResponse.getAmount())
                    .build());
        System.out.println("Second capture call: " + captureResponse2);
      }
    }
  }
}
