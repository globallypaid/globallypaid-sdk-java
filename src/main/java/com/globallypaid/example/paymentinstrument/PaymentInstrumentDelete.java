package com.globallypaid.example.paymentinstrument;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.Customer;
import com.globallypaid.service.PaymentInstrument;

import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentDelete {
    public static void main(String[] args) throws IOException, GloballyPaidException {

        new PaymentInstrument(
                Config.builder()
                        .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                        .appId(System.getenv("APP_ID"))
                        .sharedSecret(System.getenv("SHARED_SECRET"))
                        .sandbox(System.getenv("USE_SANDBOX"))
                        .build());

        try {
            String uuid = UUID.randomUUID().toString();
            Customer customer =
                    Customer.builder()
                            .clientCustomerId("Jane Doe payment instrument delete ".concat(uuid)).firstName("Jane ".concat(uuid)).lastName("Doe ".concat(uuid))
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

            PaymentInstrument.builder().build().delete(paymentInstrumentToken.getId());

            PaymentInstrumentToken retrievedPaymentInstrument =
                    paymentInstrument.retrieve(paymentInstrumentToken.getId());
            System.out.println("Retrieved PaymentInstrument: " + retrievedPaymentInstrument);
        } catch (GloballyPaidException e) {
            System.out.println(
                    "PaymentInstrument delete ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getGloballyPaidError());
        }
    }
}
