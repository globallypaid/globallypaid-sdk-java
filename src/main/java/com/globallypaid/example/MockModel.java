package com.globallypaid.example;

import com.globallypaid.model.Address;
import com.globallypaid.model.BillingContact;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.CreditCard;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.PaymentInstrument;

public class MockModel {
  private MockModel() {
    throw new UnsupportedOperationException("MockModel");
  }

  public static Address getAddress() {
    return Address.builder()
        .line1("Sun city St")
        .city("NYC")
        .state("NY")
        .postalCode("12345")
        .country("US")
        .build();
  }

  public static BillingContact getBillingContact() {
    return BillingContact.builder()
        .firstName("New Jane")
        .lastName("Doe Tester")
        .address(getAddress())
        .phone("614-340-0823")
        .email("test@test.com")
        .build();
  }

  public static BillingContact getBillingContactWithoutAddress() {
    return BillingContact.builder().firstName("New Jane").lastName("Doe").build();
  }

  public static BillingContact getBillingContactWithAddressNull() {
    return BillingContact.builder().firstName("New Jane").lastName("Doe").address(null).build();
  }

  public static BillingContact getBillingContactWithAddressEmpty() {
    return BillingContact.builder()
        .firstName("New Jane")
        .lastName("Doe")
        .address(Address.builder().build())
        .build();
  }

  public static CreditCard getCreditCard() {
    return CreditCard.builder().number("4847182731147117").expiration("0627").cvv("361").build();
  }

  public static PaymentInstrument getPaymentInstrument(boolean withCardType) {
    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .creditCard(getCreditCard())
            .billingContact(getBillingContact())
            .build();
    if (withCardType) {
      paymentInstrument.setType("creditcard");
    }

    return paymentInstrument;
  }

  public static ChargeRequest getChargeRequestWithClientInfo(String paymentInstrumentId) {
    return ChargeRequest.builder()
        .source(paymentInstrumentId)
        .amount(130)
        .currencyCode("USD")
        .clientCustomerId("4444687")
        .clientInvoiceId("123456")
        .clientTransactionId("154896575")
        .clientTransactionDescription("ChargeWithToken new Hmac - Test")
        .capture(true)
        .savePaymentInstrument(false)
        .build();
  }

  public static ChargeRequest getChargeRequestWithoutClientInfo(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
        .source(paymentInstrumentToken.getId())
        .amount(130)
        .currencyCode("USD")
        .capture(true)
        .savePaymentInstrument(false)
        .build();
  }

  public static ChargeRequest getChargeRequestWithEmptyClientInfo(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
        .source(paymentInstrumentToken.getId())
        .amount(130)
        .currencyCode("USD")
        .clientCustomerId("")
        .clientInvoiceId("")
        .clientTransactionId("    ")
        .clientTransactionDescription("    ")
        .capture(true)
        .savePaymentInstrument(false)
        .build();
  }

  public static ChargeRequest getChargeRequestWithCaptureFalse(String paymentInstrumentId) {
    return ChargeRequest.builder()
        .source(paymentInstrumentId)
        .amount(110)
        .capture(false)
        .clientCustomerId("4444687")
        .currencyCode("USD")
        .clientInvoiceId("123456")
        .clientTransactionId("154896575")
        .clientTransactionDescription("ChargeWithToken new Hmac - Test")
        .savePaymentInstrument(false)
        .build();
  }

  public static ChargeRequest getChargeRequestWithCaptureTrueAndPaymentInstrument(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
        .source(paymentInstrumentToken.getId())
        .amount(130)
        .currencyCode("USD")
        .clientCustomerId("4444687")
        .clientInvoiceId("123456")
        .clientTransactionId("154896575")
        .clientTransactionDescription("ChargeWithToken new Hmac - Test")
        .capture(true)
        .savePaymentInstrument(true)
        .build();
  }
}
