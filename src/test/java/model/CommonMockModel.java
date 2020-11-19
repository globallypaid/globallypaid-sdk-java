package model;

import com.github.javafaker.Faker;
import com.globallypaid.http.Response;
import com.globallypaid.model.Address;
import com.globallypaid.model.BillingContact;
import com.globallypaid.model.CreditCard;
import com.globallypaid.model.Entity;
import com.globallypaid.service.PaymentInstrument;
import com.globallypaid.util.JsonUtils;
import java.io.IOException;
import java.util.List;

public class CommonMockModel {
  private static final Faker faker = new Faker();

  public static Address address() {
    return Address.builder()
        .line1(faker.address().streetAddress())
        .city(faker.address().city())
        .state(faker.address().state())
        .postalCode(faker.address().zipCode())
        .country(faker.address().countryCode())
        .build();
  }

  public static BillingContact billingContact() {
    return BillingContact.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .address(address())
        .phone(faker.phoneNumber().phoneNumber())
        .email(faker.internet().emailAddress())
        .build();
  }

  public static CreditCard creditCard() {
    return CreditCard.builder()
        .number(String.valueOf(faker.number().digits(16)))
        .expiration(String.valueOf(faker.number().digits(4)))
        .cvv(String.valueOf(faker.number().digits(3)))
        .build();
  }

  public static PaymentInstrument paymentInstrument(boolean withCardType) {
    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .creditCard(creditCard())
            .billingContact(billingContact())
            .customerId(faker.lorem().word())
            .clientCustomerId(faker.lorem().word())
            .build();
    if (withCardType) {
      paymentInstrument.setType("creditcard");
    }

    return paymentInstrument;
  }

  public static <T extends Entity> Response response200WithCode00(T model) throws IOException {
    return Response.builder().statusCode(200).body(model.toJson()).build();
  }

  public static <T extends Entity> Response response200WithCode00(List<T> model)
      throws IOException {
    return Response.builder()
        .statusCode(200)
        .body(JsonUtils.convertFromObjectToJson(model))
        .build();
  }
}
