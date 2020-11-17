package com.globallypaid.example.paymentinstrument;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.Customer;
import com.globallypaid.service.PaymentInstrument;
import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentCreate {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    new PaymentInstrument(
        Config.builder()
            .apiKey(System.getenv("GLOBALLYPAID_API_KEY"))
            .appIdKey(System.getenv("GLOBALLYPAID_APP_ID_KEY"))
            .sharedSecretApiKey(System.getenv("GLOBALLYPAID_SHARED_SECRET_API_KEY"))
            .sandbox(System.getenv("GLOBALLYPAID_USE_SANDBOX"))
            .build());

    try {
      String uuid = UUID.randomUUID().toString();
      Customer customer =
          Customer.builder().clientCustomerId("Jane Doe ".concat(uuid)).build().create();
      System.out.println("Created customer: " + customer);

      // Success example
      PaymentInstrumentToken paymentInstrumentToken =
          PaymentInstrument.builder()
              .creditCard(MockModel.getCreditCard())
              .customerId(customer.getId())
              .billingContact(MockModel.getBillingContact())
              .build()
              .create(null);
      System.out.println("Created PaymentInstrument: " + paymentInstrumentToken);
    } catch (GloballyPaidException e) {
      System.out.println(
          "PaymentInstrument create ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
