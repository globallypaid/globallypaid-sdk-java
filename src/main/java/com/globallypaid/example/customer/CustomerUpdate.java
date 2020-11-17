package com.globallypaid.example.customer;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.service.Customer;
import java.io.IOException;
import java.util.UUID;

public class CustomerUpdate {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    new Customer(
        Config.builder()
            .apiKey(System.getenv("GLOBALLYPAID_API_KEY"))
            .appIdKey(System.getenv("GLOBALLYPAID_APP_ID_KEY"))
            .sharedSecretApiKey(System.getenv("GLOBALLYPAID_SHARED_SECRET_API_KEY"))
            .sandbox(System.getenv("GLOBALLYPAID_USE_SANDBOX"))
            .build());

    try {
      String uuid = UUID.randomUUID().toString();
      Customer createdCustomer =
          Customer.builder()
              .clientCustomerId("Jane Doe ".concat(uuid))
              .firstName("Jane ".concat(uuid))
              .build()
              .create();
      System.out.println("Created customer: " + createdCustomer);

      createdCustomer.setLastName("Doe updated ".concat(uuid));
      Customer updatedCustomer = createdCustomer.update(createdCustomer.getId());
      System.out.println("Updated customer: " + updatedCustomer);
    } catch (GloballyPaidException e) {
      System.out.println(
          "Customer update ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
