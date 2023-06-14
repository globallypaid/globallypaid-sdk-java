package com.deepstack.example.customer;

import com.deepstack.exception.GloballyPaidException;
import com.deepstack.http.Config;
import com.deepstack.service.Customer;
import java.io.IOException;
import java.util.List;

public class CustomersRetrieve {
  public static void main(String[] args) throws IOException, GloballyPaidException {

    new Customer(
        Config.builder()
            .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
            .appId(System.getenv("APP_ID"))
            .sharedSecret(System.getenv("SHARED_SECRET"))
            .sandbox(System.getenv("USE_SANDBOX"))
            .build());

    try {
      List<Customer> customers = Customer.builder().build().list();
      System.out.println("Retrieved customers: " + customers);
      customers.forEach(
          c -> System.out.println("Customer: " + c.getFirstName() + " " + c.getLastName()));
    } catch (GloballyPaidException e) {
      System.out.println(
          "Customer retrieve ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
    }
  }
}
