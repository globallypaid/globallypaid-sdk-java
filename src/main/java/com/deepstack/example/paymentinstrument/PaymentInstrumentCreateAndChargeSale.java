package com.deepstack.example.paymentinstrument;

import com.deepstack.example.MockModel;
import com.deepstack.exception.GloballyPaidException;
import com.deepstack.http.Config;
import com.deepstack.model.ChargeRequest;
import com.deepstack.model.CreditCard;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.common.PaymentSourceRawCard;
import com.deepstack.model.common.TransactionMeta;
import com.deepstack.model.common.TransactionParameters;
import com.deepstack.service.Customer;
import com.deepstack.service.DeepStack;
import com.deepstack.service.PaymentInstrument;

import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentCreateAndChargeSale {
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

            // Success example
            PaymentInstrumentToken paymentInstrumentToken =
                    PaymentInstrument.builder()
                            .creditCard(MockModel.getCreditCard())
                            .customerId(customer.getId())
                            .billingContact(MockModel.getBillingContact())
                            .build()
                            .create(null);
            System.out.println("Created PaymentInstrument: " + paymentInstrumentToken);

            // Fail example
            PaymentInstrumentToken paymentInstrumentToken1 =
                    PaymentInstrument.builder()
                            .creditCard(MockModel.getCreditCard())
                            //              .customerId(customer.getId())
                            .billingContact(MockModel.getBillingContactWithoutAddress())
                            .build()
                            .create(null);
            System.out.println("Created PaymentInstrument: " + paymentInstrumentToken1);

            ChargeRequest chargeRequest =
                    ChargeRequest.builder()
                            .Source(PaymentSourceRawCard.builder()
                                    .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                                    .build())
                            .Params(TransactionParameters.builder()
                                    .Amount(130)
                                    .SavePaymentInstrument(false)
                                    .Capture(true)
                                    .CurrencyCode("USD")
                                    .build())
                            .Meta(TransactionMeta.builder()
                                    .ClientCustomerID("4444687")
                                    .ClientInvoiceID("123456")
                                    .ClientTransactionDescription("ChargeWithToken new Hmac - Test")
                                    .ClientTransactionID("154896575")
                                    .build())
                            .build();
            DeepStack deepStack = new DeepStack();
            deepStack.charge(chargeRequest);

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
