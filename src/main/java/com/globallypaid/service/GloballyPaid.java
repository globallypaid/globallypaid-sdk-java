package com.globallypaid.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.globallypaid.exception.AuthenticationException;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.exception.InvalidRequestException;
import com.globallypaid.http.BasicInterface;
import com.globallypaid.http.Config;
import com.globallypaid.http.ErrorMessage;
import com.globallypaid.http.Method;
import com.globallypaid.http.Request;
import com.globallypaid.http.RequestOptions;
import com.globallypaid.http.Response;
import com.globallypaid.model.CaptureRequest;
import com.globallypaid.model.CaptureResponse;
import com.globallypaid.model.ChargeRequest;
import com.globallypaid.model.ChargeResponse;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.model.RefundRequest;
import com.globallypaid.model.RefundResponse;
import com.globallypaid.model.TokenRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GloballyPaid extends BasicInterface {

  /**
   * Initialize the client.
   *
   * @param config model {@link Config} with all configuration parameters
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public GloballyPaid(Config config) throws AuthenticationException {
    initialize(config);
  }

  /**
   * To tokenize the card data. Typically, this step is performed using our JS SDK.
   *
   * @param tokenRequest The {@link TokenRequest} object
   * @return The new {@link PaymentInstrumentToken} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public PaymentInstrumentToken token(TokenRequest tokenRequest)
      throws IOException, GloballyPaidException {
    return token(tokenRequest, null);
  }

  /**
   * To tokenize the card data with request options. Typically, this step is performed using our JS
   * SDK.
   *
   * @param tokenRequest The {@link TokenRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return The new {@link PaymentInstrumentToken} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public PaymentInstrumentToken token(TokenRequest tokenRequest, RequestOptions requestOptions)
      throws IOException, GloballyPaidException {
    this.addAuthHeader(requestOptions);
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/token")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(tokenRequest.toJson())
            .options(requestOptions)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return PaymentInstrumentToken.builder()
        .build()
        .fromJson(response.getBody(), PaymentInstrumentToken.class);
  }

  /**
   * To charge a credit card or other payment source.
   *
   * @param chargeRequest The {@link ChargeRequest} object
   * @return The new {@link ChargeResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public ChargeResponse charge(ChargeRequest chargeRequest)
      throws IOException, GloballyPaidException {
    return charge(chargeRequest, null);
  }

  /**
   * To charge a credit card or other payment source with request options.
   *
   * @param chargeRequest The {@link ChargeRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return The new {@link ChargeResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public ChargeResponse charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
      throws IOException, GloballyPaidException {
    if (!Optional.ofNullable(chargeRequest).isPresent()
        || Objects.isNull(chargeRequest.getSource())
        || chargeRequest.getSource().isEmpty()) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST, "You must provide charge source!", null, null);
    }

    this.addHmacHeader(chargeRequest.toJson(), requestOptions);
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/charge")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(chargeRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return ChargeResponse.builder().build().fromJson(response.getBody(), ChargeResponse.class);
  }

  /**
   * Capture the payment of an existing uncaptured charge. This is the second part of the two-step
   * payment flow, where first you create a charge with the capture option set to false.
   *
   * @param captureRequest The {@link CaptureRequest} object
   * @return the new {@link CaptureResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   * @see #charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
   */
  public CaptureResponse capture(CaptureRequest captureRequest)
      throws IOException, GloballyPaidException {
    return capture(captureRequest, null);
  }

  /**
   * Capture the payment of an existing uncaptured charge with request options. This is the second
   * part of the two-step payment flow, where first you create a charge with the capture option set
   * to false.
   *
   * @param captureRequest The {@link CaptureRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return the new {@link CaptureResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   * @see #charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
   */
  public CaptureResponse capture(CaptureRequest captureRequest, RequestOptions requestOptions)
      throws IOException, GloballyPaidException {
    this.addHmacHeader(captureRequest.toJson(), requestOptions);
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/capture")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(captureRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return CaptureResponse.builder().build().fromJson(response.getBody(), CaptureResponse.class);
  }

  /**
   * To refund a payment, you create a <code>RefundRequest</code> with <code>Charge</code>
   * identifier. You can also optionally refund part of a charge by specifying an amount.
   *
   * @param refundRequest The {@link RefundRequest} object
   * @return the new {@link RefundResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public RefundResponse refund(RefundRequest refundRequest)
      throws IOException, GloballyPaidException {
    return refund(refundRequest, null);
  }

  /**
   * To refund a payment, you create a <code>RefundRequest</code> with <code>Charge</code>
   * identifier. You can also optionally refund part of a charge by specifying an amount.
   *
   * @param refundRequest The {@link RefundRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return the new {@link RefundResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws GloballyPaidException In case of an API error
   */
  public RefundResponse refund(RefundRequest refundRequest, RequestOptions requestOptions)
      throws IOException, GloballyPaidException {
    this.addHmacHeader(refundRequest.toJson(), requestOptions);
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/refund")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(refundRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return RefundResponse.builder().build().fromJson(response.getBody(), RefundResponse.class);
  }
}
