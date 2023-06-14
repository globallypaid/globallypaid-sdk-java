package com.globallypaid.example.payment;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.*;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.common.PaymentInstrumentCard;
import com.globallypaid.model.common.PaymentSourceRawCard;
import com.globallypaid.model.common.TransactionMeta;
import com.globallypaid.model.common.TransactionParameters;
import com.globallypaid.service.GloballyPaid;

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
              .Line1("Sun city St")
              .City("NYC")
              .State("NY")
              .PostalCode("12345")
              .CountryCode("US")
              .build();

      BillingContact billingContact =
          BillingContact.builder()
              .FirstName("New Jane")
              .LastName("Doe Tester")
              .Address(address)
              .Phone("614-340-0823")
              .Email("test@test.com")
              .build();

      CreditCard creditCard =
          CreditCard.builder().Number("4847182731147117").Expiration("0627").CVV("361").build();

      PaymentInstrumentCard paymentInstrument =
          PaymentInstrumentCard.builder()
              .CreditCard(creditCard)
              .BillingContact(billingContact)
              .build();

      TokenRequest tokenRequest =
          TokenRequest.builder().PaymentInstrumentRequest(paymentInstrument).build();

      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(50 * 1000).build();

      PaymentInstrumentToken paymentInstrumentToken =
          globallyPaid.token(tokenRequest, requestOptions);

      if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

        ChargeRequest chargeRequest =
            ChargeRequest.builder()
                    .Source(PaymentSourceRawCard.builder()
                            .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                            .build())
                    .Params(TransactionParameters.builder()
                            .Amount(130)
                            .SavePaymentInstrument(false)
                            .Capture(true)
                            .CurrencyCode("USD")
                            .build())
                    .Meta(TransactionMeta.builder()
                            .ClientCustomerID("4444687")
                            .ClientInvoiceID("123456")
                            .ClientTransactionDescription("ChargeWithToken new Hmac - Test")
                            .ClientTransactionID("154896575")
                            .build())
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
