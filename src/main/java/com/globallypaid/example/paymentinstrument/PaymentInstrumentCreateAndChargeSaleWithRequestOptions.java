package com.globallypaid.example.paymentinstrument;

import com.globallypaid.example.MockModel;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.Customer;
import com.globallypaid.service.GloballyPaid;
import com.globallypaid.service.PaymentInstrument;

import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentCreateAndChargeSaleWithRequestOptions {
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
                    Customer.builder().clientCustomerId("Jane Doe ".concat(uuid)).firstName("Jane ".concat(uuid)).lastName("Doe ".concat(uuid)).build().create();
            System.out.println("Created customer: " + customer);

            PaymentInstrumentToken paymentInstrumentToken1 =
                    PaymentInstrument.builder()
                            .creditCard(MockModel.getCreditCard())
                            .customerId(customer.getId())
                            .billingContact(MockModel.getBillingContact())
                            .build()
                            .create(null);
            System.out.println("Created PaymentInstrument: " + paymentInstrumentToken1);

            try {
                RequestOptions requestOptions =
                        RequestOptions.builder()
                                .publishableApiKey("")
                                .appId("123641")
                                .sharedSecret("1452456465455648321564564561")
                                .build();
                PaymentInstrumentToken paymentInstrumentToken =
                        PaymentInstrument.builder()
                                .creditCard(MockModel.getCreditCard())
                                .customerId(customer.getId())
                                .clientCustomerId("")
                                .billingContact(MockModel.getBillingContact())
                                .build()
                                .create(requestOptions);
                System.out.println("Created PaymentInstrument: " + paymentInstrumentToken);
            } catch (GloballyPaidException e) {
                System.out.println(
                        "PaymentInstrument create ---> Code: "
                                + e.getCode()
                                + "\nMsg: "
                                + e.getMessage()
                                + "\nApi error: "
                                + e.getGloballyPaidError());
            }

            ChargeRequest chargeRequest =
                    ChargeRequest.builder()
                            .source(paymentInstrumentToken1.getId())
                            .amount(304)
                            .currencyCode("USD")
                            .clientCustomerId("54545121")
                            .clientInvoiceId("123456")
                            .clientTransactionId("154896575")
                            .clientTransactionDescription("ChargeWithToken new Hmac - Test with charge false")
                            .savePaymentInstrument(false)
                            .build();
            GloballyPaid globallyPaid = new GloballyPaid();
            System.out.println("charge: " + globallyPaid.charge(chargeRequest));
        } catch (GloballyPaidException e) {
            System.out.println(
                    "PaymentInstrument create ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getGloballyPaidError());
        }
    }
}
