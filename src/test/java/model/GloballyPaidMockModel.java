package model;

import com.github.javafaker.Faker;
import com.globallypaid.model.CaptureRequest;
import com.globallypaid.model.CaptureResponse;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.RefundRequest;
import com.globallypaid.model.RefundResponse;
import com.globallypaid.model.TokenRequest;
import com.globallypaid.service.Customer;

public class GloballyPaidMockModel {

  public static final String APPROVED = "Approved";
  public static final String RESPONSE_CODE = "00";
  private static final Faker faker = new Faker();

  public static ChargeRequest getChargeRequestWithCaptureTrueAndClientInfo() {
    return ChargeRequest.builder()
        .source(faker.lorem().fixedString(20))
        .amount(faker.number().numberBetween(100, 200))
        .currencyCode(faker.currency().code())
        .clientCustomerId(String.valueOf(faker.number().numberBetween(1, 10)))
        .clientInvoiceId(String.valueOf(faker.number().numberBetween(1, 10)))
        .clientTransactionId(String.valueOf(faker.number().numberBetween(1, 10)))
        .clientTransactionDescription(faker.lorem().word())
        .capture(Boolean.TRUE)
        .savePaymentInstrument(Boolean.FALSE)
        .build();
  }

  public static ChargeRequest chargeRequestWithoutSource() {
    return ChargeRequest.builder().capture(Boolean.TRUE).build();
  }

  public static ChargeRequest chargeRequestOnlyWithSource() {
    return ChargeRequest.builder().source(faker.lorem().fixedString(20)).build();
  }

  public static TokenRequest tokenRequest() {
    return TokenRequest.builder()
        .paymentInstrument(CommonMockModel.paymentInstrument(true))
        .build();
  }

  public static ChargeResponse chargeResponseForCaptureTrueAndClientInfo(Integer amount) {
    return ChargeResponse.builder()
        .id(faker.lorem().word())
        .responseCode(RESPONSE_CODE)
        .message(APPROVED)
        .approved(Boolean.TRUE)
        .amount(amount)
        .build();
  }

  public static PaymentInstrumentToken paymentInstrumentToken(TokenRequest tokenRequest) {
    return PaymentInstrumentToken.builder()
        .id(faker.lorem().word())
        .billingContact(tokenRequest.getPaymentInstrument().getBillingContact())
        .clientCustomerId(tokenRequest.getPaymentInstrument().getClientCustomerId())
        .clientId(tokenRequest.getPaymentInstrument().getClientId())
        .customerId(tokenRequest.getPaymentInstrument().getCustomerId())
        .brand(tokenRequest.getPaymentInstrument().getCreditCard().getBrand())
        .expiration(tokenRequest.getPaymentInstrument().getCreditCard().getExpiration())
        .lastFour(tokenRequest.getPaymentInstrument().getCreditCard().getLastFour())
        .type(tokenRequest.getPaymentInstrument().getType())
        .build();
  }

  public static PaymentInstrumentToken paymentInstrumentTokenWithCustomer(
      TokenRequest tokenRequest, Customer customer) {
    return PaymentInstrumentToken.builder()
        .id(faker.lorem().word())
        .billingContact(tokenRequest.getPaymentInstrument().getBillingContact())
        .clientCustomerId(tokenRequest.getPaymentInstrument().getClientCustomerId())
        .clientId(tokenRequest.getPaymentInstrument().getClientId())
        .customerId(customer.getId())
        .brand(tokenRequest.getPaymentInstrument().getCreditCard().getBrand())
        .expiration(tokenRequest.getPaymentInstrument().getCreditCard().getExpiration())
        .lastFour(tokenRequest.getPaymentInstrument().getCreditCard().getLastFour())
        .type(tokenRequest.getPaymentInstrument().getType())
        .build();
  }

  public static CaptureRequest captureRequest() {
    return CaptureRequest.builder()
        .amount(faker.number().numberBetween(100, 200))
        .charge(faker.lorem().fixedString(20))
        .build();
  }

  public static CaptureResponse captureResponse(CaptureRequest captureRequest) {
    return CaptureResponse.builder()
        .amount(captureRequest.getAmount())
        .chargeTransactionID(captureRequest.getCharge())
        .responseCode(RESPONSE_CODE)
        .message(APPROVED)
        .approved(Boolean.TRUE)
        .isRecurring(false)
        .build();
  }

  public static RefundRequest refundRequest() {
    return RefundRequest.builder()
        .amount(faker.number().numberBetween(100, 200))
        .charge(faker.lorem().fixedString(20))
        .build();
  }

  public static RefundResponse refundResponse(RefundRequest refundRequest) {
    return RefundResponse.builder()
        .amount(refundRequest.getAmount())
        .chargeTransactionID(refundRequest.getCharge())
        .responseCode(RESPONSE_CODE)
        .message(APPROVED)
        .approved(Boolean.TRUE)
        .build();
  }
}
