package com.globallypaid.example.paymentinstrument;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.Customer;
import com.globallypaid.service.PaymentInstrument;
import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentUpdate {
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
          Customer.builder()
              .clientCustomerId("Jane Doe payment instrument update ".concat(uuid))
              .build()
              .create();
      System.out.println("Created customer: " + customer);

      PaymentInstrument paymentInstrument =
          PaymentInstrument.builder()
              .creditCard(MockModel.getCreditCard())
              .customerId(customer.getId())
              .build();
      PaymentInstrumentToken paymentInstrumentToken = paymentInstrument.create();
      System.out.println("Created PaymentInstrument: " + paymentInstrumentToken);

      PaymentInstrumentToken retrievedPaymentInstrument =
          paymentInstrument.retrieve(paymentInstrumentToken.getId());
      System.out.println("Retrieved PaymentInstrument: " + retrievedPaymentInstrument);

      //      PaymentInstrument paymentInstrumentToUpdate =
      //          PaymentInstrument.builder()
      //              .id(retrievedPaymentInstrument.getId())
      //              .clientCustomerId(retrievedPaymentInstrument.getClientCustomerId())
      //              .type(retrievedPaymentInstrument.getType())
      //              .customerId("123456")
      //              .billingContact(retrievedPaymentInstrument.getBillingContact())
      //              .clientId(retrievedPaymentInstrument.getClientId())
      //              .build();
      //      PaymentInstrumentToken updatedPaymentInstrumentToken =
      //          paymentInstrumentToUpdate.update(retrievedPaymentInstrument.getId());

      paymentInstrument.setId(retrievedPaymentInstrument.getId());
      paymentInstrument.setClientCustomerId("456789");
      PaymentInstrumentToken updatedPaymentInstrumentToken =
          paymentInstrument.update(retrievedPaymentInstrument.getId());
      System.out.println("Updated PaymentInstrument: " + updatedPaymentInstrumentToken);
    } catch (GloballyPaidException e) {
      System.out.println(
          "PaymentInstrument update ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
