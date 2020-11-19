package com.globallypaid.example.paymentinstrument;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.PaymentInstrument;
import java.io.IOException;

public class PaymentInstrumentRetrieveNotFound {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    PaymentInstrument paymentInstrument =
        new PaymentInstrument(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    try {
      PaymentInstrumentToken retrievedPaymentInstrument = paymentInstrument.retrieve("123455");
      System.out.println("Retrieved PaymentInstrument: " + retrievedPaymentInstrument);
    } catch (GloballyPaidException e) {
      System.out.println(
          "PaymentInstrument retrieve ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
