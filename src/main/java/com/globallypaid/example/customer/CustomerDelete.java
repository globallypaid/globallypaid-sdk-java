package com.globallypaid.example.customer;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.service.Customer;
import java.io.IOException;
import java.util.UUID;

public class CustomerDelete {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    Customer customer =
        new Customer(
            Config.builder()
                .apiKey(System.getenv("GLOBALLYPAID_API_KEY"))
                .appIdKey(System.getenv("GLOBALLYPAID_APP_ID_KEY"))
                .sharedSecretApiKey(System.getenv("GLOBALLYPAID_SHARED_SECRET_API_KEY"))
                .sandbox(System.getenv("GLOBALLYPAID_USE_SANDBOX"))
                .build());

    try {
      customer.setClientCustomerId("Jane Doe delete ".concat(UUID.randomUUID().toString()));
      customer.setFirstName("Jane ".concat(UUID.randomUUID().toString()));
      Customer createdCustomer = customer.create();
      System.out.println("Created customer: " + createdCustomer);
      customer.delete(createdCustomer.getId());

      System.out.println("Retrieved customer: " + customer.retrieve(createdCustomer.getId()));
    } catch (GloballyPaidException e) {
      System.out.println(
          "Customer delete ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
