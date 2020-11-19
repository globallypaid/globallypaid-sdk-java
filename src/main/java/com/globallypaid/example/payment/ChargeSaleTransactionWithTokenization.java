package com.globallypaid.example.payment;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.Address;
import com.globallypaid.model.BillingContact;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.CreditCard;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.service.GloballyPaid;
import com.globallypaid.service.PaymentInstrument;
import java.io.IOException;

public class ChargeSaleTransactionWithTokenization {
  public static void main(String[] args) throws IOException, GloballyPaidException {
    try {
      GloballyPaid globallyPaid =
          new GloballyPaid(
              Config.builder()
                  .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                  .appId(System.getenv("APP_ID"))
                  .sharedSecret(System.getenv("SHARED_SECRET"))
                  .sandbox(System.getenv("USE_SANDBOX"))
                  .build());

      Address address =
          Address.builder()
              .line1("Sun city St")
              .city("NYC")
              .state("NY")
              .postalCode("12345")
              .country("US")
              .build();

      BillingContact billingContact =
          BillingContact.builder()
              .firstName("New Jane")
              .lastName("Doe Tester")
              .address(address)
              .phone("614-340-0823")
              .email("test@test.com")
              .build();

      CreditCard creditCard =
          CreditCard.builder().number("4847182731147117").expiration("0627").cvv("361").build();

      PaymentInstrument paymentInstrument =
          PaymentInstrument.builder()
              .type("creditcard")
              .creditCard(creditCard)
              .billingContact(billingContact)
              .build();

      TokenRequest tokenRequest =
          TokenRequest.builder().paymentInstrument(paymentInstrument).build();

      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(50 * 1000).build();

      PaymentInstrumentToken paymentInstrumentToken =
          globallyPaid.token(tokenRequest, requestOptions);

      if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

        ChargeRequest chargeRequest =
            ChargeRequest.builder()
                .source(paymentInstrumentToken.getId())
                .amount(130)
                .currencyCode("USD")
                .clientCustomerId("4444687")
                .clientInvoiceId("123456")
                .clientTransactionId("154896575")
                .clientTransactionDescription("ChargeWithToken new Hmac - Test")
                .capture(true)
                .savePaymentInstrument(false)
                .build();

        ChargeResponse chargeResponse = GloballyPaid.builder().build().charge(chargeRequest);
        System.out.println(chargeResponse);
      }
    } catch (GloballyPaidException e) {
      System.out.println(
          "ChargeSaleTrans ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
      throw e;
    }
  }
}
