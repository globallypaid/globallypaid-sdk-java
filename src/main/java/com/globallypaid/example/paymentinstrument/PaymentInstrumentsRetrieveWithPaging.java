package com.globallypaid.example.paymentinstrument;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.Customer;
import com.globallypaid.service.PaymentInstrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PaymentInstrumentsRetrieveWithPaging {
    public static void main(String[] args) throws IOException, GloballyPaidException {

        PaymentInstrument paymentInstrument =
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
                            .clientCustomerId(
                                    "John Doe payment instrument list ".concat(uuid)).firstName("Jane ".concat(uuid)).lastName("Doe ".concat(uuid))
                            .build()
                            .create();
            System.out.println("Created customer: " + customer);

            List<String> generatedPaymentInstrumentIds = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                PaymentInstrumentToken paymentInstrumentToken =
                        PaymentInstrument.builder()
                                .creditCard(MockModel.getCreditCard())
                                .customerId(customer.getId())
                                .build()
                                .create();
                generatedPaymentInstrumentIds.add(paymentInstrumentToken.getId());
            }
            generatedPaymentInstrumentIds =
                    generatedPaymentInstrumentIds.stream().sorted().collect(Collectors.toList());
            System.out.println(
                    "\nGenerated PaymentInstrument Ids: " + generatedPaymentInstrumentIds.toString());

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("size", "1");
            queryParams.put("index", "1");
            List<PaymentInstrumentToken> retrievedPaymentInstruments =
                    paymentInstrument.list(customer.getId(), queryParams, null);

            List<String> retrievedPaymentInstrumentIds =
                    retrievedPaymentInstruments.stream()
                            .map(PaymentInstrumentToken::getId)
                            .sorted()
                            .collect(Collectors.toList());

            System.out.println(
                    "\nRetrieved PaymentInstrument Ids: " + retrievedPaymentInstrumentIds.toString());

            System.out.println(
                    "\nAll records are retrived? - "
                            + generatedPaymentInstrumentIds.equals(retrievedPaymentInstrumentIds));
        } catch (GloballyPaidException e) {
            System.out.println(
                    "PaymentInstruments retrieve ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getGloballyPaidError());
        }
    }
}
