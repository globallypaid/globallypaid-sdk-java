# GloballyPaid Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/com.globallypaid/globallypaid-java.svg?label=Maven%20Central)](https://search.maven.org/artifact/com.globallypaid/globallypaid-java)
[![Build Status](https://github.com/globallypaid/globallypaid-sdk-java/workflows/CI/badge.svg)](https://github.com/globallypaid/globallypaid-sdk-java/actions)
[![JavaDoc](http://img.shields.io/badge/javadoc-reference-blue.svg)][javadoc]

The official [GloballyPaid][globallypaid] Java library.

# Table of Contents

* [Installation](#installation)
* [Documentation](#documentation)
* [Samples](#samples)
* [Quick Start](#quick-start)
    * [Make a Charge Sale Transaction](#charge-trans)
    * [Make a Charge Sale Transaction with Javascript SDK integration](#charge-trans-js-sdk)
* [Configuring Timeouts](#timeouts)   
* [About](#about)

<a name="installation"></a>
## Installation

### Requirements

- Java 1.8 or later

#### Gradle users

Add this dependency to your project's build file in the root:

```groovy
...
dependencies {
    ...
    implementation "com.globallypaid:globallypaid-java:1.0.1"
}

repositories {
    mavenCentral()
}
...
```

#### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.globallypaid</groupId>
  <artifactId>globallypaid-java</artifactId>
  <version>1.0.1</version>
</dependency>
```

<a name="documentation"></a>
## Documentation

Please see the [Java API docs][api-docs] for the most
up-to-date documentation.

You can also refer to the [online Javadoc][javadoc].

<a name="samples"></a>
## Samples

For a sample project, please visit [GloballyPaid Java SDK samples][java-sdk-sample].

For a comprehensive list of examples, please visit project's [examples][examples].

<a name="quick-start"></a>
## Quick Start

The following is the minimum needed code to make a charge sale transaction:

<a name="env-variables"></a>
### Environment Variables

`globallypaid-java` supports the GloballyPaid Api Key, App ID and Shared Secret 
values stored in the following environment variables:

* `PUBLISHABLE_API_KEY`
* `APP_ID`
* `SHARED_SECRET`
* `USE_SANDBOX`

### Setup Environment Variables

Update the development environment with your [Environment Variables](#env-variables) 
with the following steps:

#### Linux users:

1. Copy the sample environment file `env_sample.sh` to a new file
```bash
cp env_sample.sh globallypaid_env.sh
```
2. Edit the new `globallypaid_env.sh` to add your [Environment Variables](#env-variables)
3. Source the `globallypaid_env.sh` file to set the variables in the current session
```bash
source globallypaid_env.sh
```

#### Windows users:

1. Copy the sample environment file `env_sample.bat` to a new file
```bash
cp env_sample.bat globallypaid_env.bat
```
2. Edit the new `globallypaid_env.bat` to add your [Environment Variables](#env-variables)
3. Execute the `globallypaid_env.bat` file to set the variables
```bash
globallypaid_env.bat
```

### Initialize the Client
```java
GloballyPaid globallyPaid = new GloballyPaid(
          Config.builder()
              .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
              .appId(System.getenv("APP_ID"))
              .sharedSecret(System.getenv("SHARED_SECRET"))
              .sandbox(System.getenv("USE_SANDBOX")) // true if you need to test through GloballyPaid sandbox
              .build());
```
or
```java
String publishableApiKey = "pk_live_xxxxx";
String appId = "Your APP ID";
String sharedSecret = "Your Shared Secret";

GloballyPaid globallyPaid = new GloballyPaid(
          Config.builder()
              .publishableApiKey(publishableApiKey)
              .appId(appId)
              .sharedSecret(sharedSecret)              
              .build());
```

You can also change to sandbox with the following setting:
```java
GloballyPaid.setSandbox(true);
```

##### Per-request configuration

All SDK service methods accept an optional `RequestOptions` object, additionally allowing per-request configuration:
```java
RequestOptions requestOptions = RequestOptions.builder()
            .appId("Your APP ID")
            .sharedSecret("Your Shared Secret")
            .build();

Customer.builder().build().retrieve("customer_id_here", requestOptions);

RequestOptions requestOptions = RequestOptions.builder()
            .publishableApiKey("Your Publishable API Key").build();

GloballyPaid.builder().build().token("token_request_here", requestOptions);
```

<a name="timeouts"></a>
### Configuring Timeouts

Connect and read timeouts can be configured globally:

```java
GloballyPaid.setConnectTimeout(50 * 1000); // in milliseconds
GloballyPaid.setReadTimeout(100 * 1000);
```

Or on a finer grain level using `RequestOptions`:

```java
RequestOptions options = RequestOptions.builder()
    .connectTimeout(50 * 1000) // in milliseconds
    .readTimeout(100*1000)
    .build();
GloballyPaid.builder().build().charge(ChargeRequest.builder().build(), requestOptions);
```

Please take care to set conservative read timeouts. Some API requests can take
some time, and a short timeout increases the likelihood of a problem within our
servers.

<a name="charge-trans"></a>
### Make a Charge Sale Transaction

GloballyPaid Charge Sale Transaction example:

```java
package com.globallypaid.example.payment;

import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.service.GloballyPaid;
import java.io.IOException;

public class ChargeSaleTransaction {
  public static void main(String[] args) throws IOException, GloballyPaidException {
    try {
      GloballyPaid globallyPaid =
          new GloballyPaid(
              Config.builder()
                  .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                  .appId(System.getenv("APP_ID"))
                  .sharedSecret(System.getenv("SHARED_SECRET"))
                  .sandbox(System.getenv("USE_SANDBOX"))
                  .build());

      ChargeRequest chargeRequest =
          ChargeRequest.builder()
              .source("source") // can be the token or payment instrument identifier
              .amount(130)
              .currencyCode("USD")
              .clientCustomerId("XXXXXXX") // set your customer id
              .clientInvoiceId("XXXXXX") // set your invoice id
              .clientTransactionId("XXXXXXXXX")
              .clientTransactionDescription("Charge Sale Transaction") // set your transaction description
              .capture(true) // sale charge
              .savePaymentInstrument(false)
              .build();

      ChargeResponse chargeResponse = globallyPaid.charge(chargeRequest, null);
      System.out.println(chargeResponse);
    } catch (GloballyPaidException e) {
      System.out.println(
          "ChargeSaleTransaction ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getGloballyPaidError());
      throw e;
    }
  }
}
```

See the project's [examples][examples] for more examples.

<a name="charge-trans-js-sdk"></a>
### Make a Charge Sale Transaction with Javascript SDK integration
```java
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.http.Config;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

  public ChargeResponse charge(PaymentInstrumentToken paymentInstrumentToken)
      throws GloballyPaidException {

    ChargeResponse chargeResponse = null;

    GloballyPaid globallyPaid =
        new GloballyPaid(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).readTimeout(100 * 1000).build();

      ChargeRequest gpChargeRequest =
          ChargeRequest.builder()
              .source(paymentInstrumentToken.getId())
              .amount(160)
              .currencyCode("USD")
              .clientCustomerId("XXXXXXX")
              .clientInvoiceId("XXXXXX")
              .clientTransactionId("XXXXXXXXX")
              .clientTransactionDescription("Charge Sale sample!")
              .capture(true)
              .savePaymentInstrument(false)
              .build();

      chargeResponse = globallyPaid.charge(gpChargeRequest, requestOptions);
      System.out.println(chargeResponse.toString());
    }
    return chargeResponse;
  }
}
```

Please visit [GloballyPaid Java SDK samples][java-sdk-sample] to see sample project.

<a name="about"></a>
## About
globallypaid-java is maintained and funded by Globally Paid.

If you've found a bug in the library or would like new features added, 
go ahead and [open issues][java-sdk-open-issues] or [pull requests][java-sdk-pull-requests]!

[globallypaid]: https://globallypaid.com/
[api-docs]: https://docs.globallypaid.com/?java
[javadoc]: https://www.javadoc.io/doc/com.globallypaid/globallypaid-java/latest/index.html
[java-sdk-open-issues]: https://github.com/globallypaid/globallypaid-sdk-java/issues
[java-sdk-pull-requests]: https://github.com/globallypaid/globallypaid-sdk-java/pulls
[examples]: https://github.com/globallypaid/globallypaid-sdk-java/blob/master/src/main/java/com/globallypaid/example/
[java-sdk-sample]: https://github.com/globallypaid/globallypaid-sdk-java-samples
[support-center]: https://globallypaid.com/contact-us/
