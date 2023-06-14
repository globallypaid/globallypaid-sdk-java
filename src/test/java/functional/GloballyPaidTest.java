package functional;

import com.globallypaid.exception.ApiException;
import com.globallypaid.exception.AuthenticationException;
import com.globallypaid.exception.ForbiddenException;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.exception.InvalidRequestException;
import com.globallypaid.exception.NotAcceptableException;
import com.globallypaid.exception.NotAllowedException;
import com.globallypaid.exception.RateLimitException;
import com.globallypaid.http.ErrorMessage;
import com.globallypaid.http.Response;
import com.globallypaid.model.*;
import com.globallypaid.model.PaymentInstrumentToken;
import com.globallypaid.service.GloballyPaid;
import java.io.IOException;
import java.util.Objects;
import model.CommonMockModel;
import model.GloballyPaidMockModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class GloballyPaidTest {

  @Mock GloballyPaid globallyPaid;

  @Test
  public void testChargeSaleTransactionWithClientInfo() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest =
        GloballyPaidMockModel.getChargeRequestWithCaptureTrueAndClientInfo();

    ChargeResponse expectedChargeResponse =
        GloballyPaidMockModel.chargeResponseForCaptureTrueAndClientInfo(chargeRequest.getParams().getAmount());
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedChargeResponse);

    lenient().when(globallyPaid.api(any())).thenReturn(expectedResponse);
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    ChargeResponse actualResponse = globallyPaid.charge(chargeRequest);

    assertTrue(Objects.nonNull(actualResponse));
    assertEquals(expectedChargeResponse.getID(), actualResponse.getID());
  }

  @Test
  public void testChargeWithoutSourceFailWith400() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestWithoutSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(400, exception.getCode());
    assertTrue(exception.getMessage().contains("charge source"));
  }

  @Test
  public void testChargeFailWith400() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith404() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith401() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(AuthenticationException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith403() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ForbiddenException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith405() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAllowedException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith406() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAcceptableException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith410() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith429() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(RateLimitException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith503() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testChargeFailWith500() throws GloballyPaidException, IOException {
    ChargeRequest chargeRequest = GloballyPaidMockModel.chargeRequestOnlyWithSource();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(globallyPaid.charge(any())).thenCallRealMethod();
    lenient().when(globallyPaid.charge(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.charge(chargeRequest));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureSuccess() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();

    CaptureResponse expectedCaptureResponse = GloballyPaidMockModel.captureResponse(captureRequest);
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedCaptureResponse);

    lenient().when(globallyPaid.api(any())).thenReturn(expectedResponse);
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    CaptureResponse actualResponse = globallyPaid.capture(captureRequest);

    assertTrue(Objects.nonNull(actualResponse));
    assertEquals(
        expectedCaptureResponse.getChargeTransactionID(), actualResponse.getChargeTransactionID());
    assertEquals(expectedCaptureResponse.getAmount(), actualResponse.getAmount());
    assertEquals(GloballyPaidMockModel.RESPONSE_CODE, actualResponse.getResponseCode());
    assertTrue(actualResponse.isApproved());
  }

  @Test
  public void testCaptureFailWith400() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith404() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith401() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(AuthenticationException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith403() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ForbiddenException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith405() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAllowedException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith406() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAcceptableException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith410() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith429() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(RateLimitException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith503() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCaptureFailWith500() throws GloballyPaidException, IOException {
    CaptureRequest captureRequest = GloballyPaidMockModel.captureRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(globallyPaid.capture(any())).thenCallRealMethod();
    lenient().when(globallyPaid.capture(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.capture(captureRequest));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundSuccess() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();

    RefundResponse expectedRefundResponse = GloballyPaidMockModel.refundResponse(refundRequest);
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedRefundResponse);

    lenient().when(globallyPaid.api(any())).thenReturn(expectedResponse);
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    RefundResponse actualResponse = globallyPaid.refund(refundRequest);

    assertTrue(Objects.nonNull(actualResponse));
    assertEquals(
        expectedRefundResponse.getChargeTransactionID(), actualResponse.getChargeTransactionID());
    assertEquals(expectedRefundResponse.getAmount(), actualResponse.getAmount());
    assertEquals(GloballyPaidMockModel.RESPONSE_CODE, actualResponse.getResponseCode());
    assertTrue(actualResponse.isApproved());
  }

  @Test
  public void testRefundFailWith400() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith404() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith401() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(AuthenticationException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith403() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ForbiddenException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith405() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAllowedException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith406() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAcceptableException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith410() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith429() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(RateLimitException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith503() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRefundFailWith500() throws GloballyPaidException, IOException {
    RefundRequest refundRequest = GloballyPaidMockModel.refundRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(globallyPaid.refund(any())).thenCallRealMethod();
    lenient().when(globallyPaid.refund(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.refund(refundRequest));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeSuccess() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();

    PaymentInstrumentToken expectedTokenResponse =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedTokenResponse);

    lenient().when(globallyPaid.tokenapi(any())).thenReturn(expectedResponse);
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    PaymentInstrumentToken actualResponse = globallyPaid.token(tokenRequest);

    assertTrue(Objects.nonNull(actualResponse));
    assertTrue(Objects.nonNull(actualResponse.getId()));
    assertEquals(expectedTokenResponse.getType(), actualResponse.getType());
    assertEquals(expectedTokenResponse.getLastFour(), actualResponse.getLastFour());
    assertEquals(expectedTokenResponse.getBrand(), actualResponse.getBrand());
    assertEquals(expectedTokenResponse.getExpiration(), actualResponse.getExpiration());
    assertEquals(expectedTokenResponse.getClientCustomerId(), actualResponse.getClientCustomerId());
  }

  @Test
  public void testTokenizeFailWith400() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith404() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(InvalidRequestException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith401() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(AuthenticationException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith403() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ForbiddenException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith405() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAllowedException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith406() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(NotAcceptableException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith410() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith429() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(RateLimitException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith503() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testTokenizeFailWith500() throws GloballyPaidException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    lenient()
        .when(globallyPaid.tokenapi(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(globallyPaid.token(any())).thenCallRealMethod();
    lenient().when(globallyPaid.token(any(), any())).thenCallRealMethod();

    GloballyPaidException exception =
        assertThrows(ApiException.class, () -> globallyPaid.token(tokenRequest));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  // TODO handle null request
  //  @Test
  //  public void testCaptureWithNullIdFail() throws IOException, GloballyPaidException {
  //    when(globallyPaid.capture(null)).thenCallRealMethod();
  //    when(globallyPaid.capture(null, null)).thenCallRealMethod();
  //    GloballyPaidException exception =
  //        assertThrows(InvalidRequestException.class, () -> globallyPaid.capture(null));
  //
  //    assertEquals(400, exception.getCode());
  //    assertTrue(exception.getMessage().contains("Invalid null ID"));
  //  }
}
