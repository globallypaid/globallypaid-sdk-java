package com.globallypaid.example.payment;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.service.GloballyPaid;
import com.globallypaid.service.PaymentInstrument;
import java.io.IOException;
import org.apache.http.util.TextUtils;

public class ChargeWithPaymentInstrumentTrans {
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

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(50 * 1000).build();
      paymentInstrumentToken = globallyPaid.token(tokenRequest, requestOptions);
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
            globallyPaid.charge(
                MockModel.getChargeRequestWithCaptureTrueAndPaymentInstrument(
                    paymentInstrumentToken));
        System.out.println(chargeResponse.toString());

        if (chargeResponse.getPaymentInstrument() != null
            && !TextUtils.isBlank(chargeResponse.getPaymentInstrument().getId())) {
          ChargeRequest chargeWithPaymentInstrument =
              MockModel.getChargeRequestWithClientInfo(
                  chargeResponse.getPaymentInstrument().getId());
          chargeWithPaymentInstrument.setAmount(150);

          try {
            ChargeResponse chargeResponseWithPI = globallyPaid.charge(chargeWithPaymentInstrument);
            System.out.println("chargeResponseWithPI: " + chargeResponseWithPI.toString());
          } catch (GloballyPaidException e) {
            System.out.println(
                "ChargeTransactionWithPaymentInstrument ---> Code: "
                    + e.getCode()
                    + "\nMsg: "
                    + e.getMessage()
                    + "\nApi error: "
                    + e.getGloballyPaidError());
          }
        }

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
