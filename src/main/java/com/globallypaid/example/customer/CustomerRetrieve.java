package com.globallypaid.example.customer;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.service.Customer;

import java.io.IOException;
import java.util.UUID;

public class CustomerRetrieve {
    public static void main(String[] args) throws IOException, GloballyPaidException {

        Customer customer =
                new Customer(
                        Config.builder()
                                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                                .appId(System.getenv("APP_ID"))
                                .sharedSecret(System.getenv("SHARED_SECRET"))
                                .sandbox(System.getenv("USE_SANDBOX"))
                                .build());

        try {
            customer.setClientCustomerId("Jane Doe ".concat(UUID.randomUUID().toString()));
            customer.setFirstName("Jane ".concat(UUID.randomUUID().toString()));
            customer.setLastName("Doe ".concat(UUID.randomUUID().toString()));
            Customer createdCustomer = customer.create();
            System.out.println("Created customer: " + createdCustomer);

            Customer retrievedCustomer = customer.retrieve(createdCustomer.getId());
            System.out.println("Retrieved customer: " + retrievedCustomer);
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
