package com.globallypaid.http;

import com.globallypaid.exception.AuthenticationException;
import com.globallypaid.exception.GloballyPaidException;
import com.globallypaid.exception.InvalidRequestException;
import com.globallypaid.model.Entity;
import com.globallypaid.util.HmacUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpStatus;
import org.apache.http.util.TextUtils;


import static com.globallypaid.util.Constants.API_BASE_URL;
import static com.globallypaid.util.Constants.API_VERSION;
import static com.globallypaid.util.Constants.HMAC_ALGORITHM_TYPE;
import static com.globallypaid.util.Constants.HMAC_HEADER;
import static com.globallypaid.util.Constants.LIVE_BASE_URL;
import static com.globallypaid.util.Constants.SANDBOX_BASE_URL;

public abstract class BasicInterface extends Entity {
  public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
  public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

  /** The HTTP client. */
  private Client client;

  /** The API version. */
  private static String version;

  /** The request headers container. */
  private Map<String, String> requestHeaders = new HashMap<>();

  private static volatile String apiKey;
  private static volatile String appIdKey;
  private static volatile String sharedSecretApiKey;
  private static volatile String baseUrl = LIVE_BASE_URL;

  private static volatile int connectTimeout = -1;
  private static volatile int readTimeout = -1;

  private static volatile int maxNetworkRetries = 0;

  private static boolean sandbox;

  public BasicInterface(final Client client) {
    this.client = client;
  }

  public BasicInterface() {
    this.client = new Client();
  }

  public static void setSandbox(boolean sandbox) {
    BasicInterface.sandbox = sandbox;
    baseUrl = sandbox ? SANDBOX_BASE_URL : LIVE_BASE_URL;
  }

  /**
   * Initialize the client.
   *
   * @param config model {@link Config} with all configuration parameters.
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public static void initialize(Config config) throws AuthenticationException {
    if (TextUtils.isBlank(config.getApiKey())
        || TextUtils.isBlank(config.getAppIdKey())
        || TextUtils.isBlank(config.getSharedSecretApiKey())) {
      throw new AuthenticationException("Api and App keys must be defined!");
    }

    apiKey = "Bearer ".concat(config.getApiKey());
    appIdKey = config.getAppIdKey();
    sharedSecretApiKey = config.getSharedSecretApiKey();
    sandbox =
        !TextUtils.isBlank(config.getSandbox())
            && "true".equalsIgnoreCase(config.getSandbox().trim());
    baseUrl =
        !TextUtils.isBlank(config.getSandbox())
                && "true".equalsIgnoreCase(config.getSandbox().trim())
            ? SANDBOX_BASE_URL
            : LIVE_BASE_URL;
    version = API_VERSION;
  }

  public static void setVersion(String version) {
    BasicInterface.version = version;
  }

  /**
   * Returns the connection timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getConnectTimeout() {
    if (connectTimeout == -1) {
      return DEFAULT_CONNECT_TIMEOUT;
    }
    return connectTimeout;
  }

  /**
   * Sets the timeout value that will be used for making new connections to the GloballyPaid API (in
   * milliseconds).
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setConnectTimeout(final int timeout) {
    connectTimeout = timeout;
  }

  /**
   * Returns the read timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getReadTimeout() {
    if (readTimeout == -1) {
      return DEFAULT_READ_TIMEOUT;
    }
    return readTimeout;
  }

  /**
   * Sets the timeout value that will be used when reading data from an established connection to
   * the GloballyPaid API (in milliseconds).
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setReadTimeout(final int timeout) {
    readTimeout = timeout;
  }

  /**
   * Returns the maximum number of times requests will be retried.
   *
   * @return the maximum number of times requests will be retried
   */
  public static int getMaxNetworkRetries() {
    return maxNetworkRetries;
  }

  /**
   * Sets the maximum number of times requests will be retried.
   *
   * @param maxNetworkRetries the maximum number of times requests will be retried
   */
  public static void setMaxNetworkRetries(int maxNetworkRetries) {
    BasicInterface.maxNetworkRetries = maxNetworkRetries;
  }

  /**
   * Initialize the client with authorization header.
   *
   * @throws AuthenticationException In case of a bad defined API key
   */
  public void addAuthHeader() throws AuthenticationException {
    if (TextUtils.isBlank(apiKey)) {
      throw new AuthenticationException("Api key must be defined!");
    }

    this.requestHeaders.put("Authorization", apiKey);
  }

  /**
   * Initialize the client with HMAC header.
   *
   * @param content The request content
   * @throws InvalidRequestException In case of a problem during HMAC generation
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public void addHmacHeader(String content)
      throws InvalidRequestException, AuthenticationException {
    if (TextUtils.isBlank(appIdKey) || TextUtils.isBlank(sharedSecretApiKey)) {
      throw new AuthenticationException("Api and App keys must be defined!");
    }
    String hmac =
        HmacUtils.createHmacHeader(content, sharedSecretApiKey, appIdKey, HMAC_ALGORITHM_TYPE);
    this.requestHeaders.put(HMAC_HEADER, hmac);
  }

  public Map<String, String> getRequestHeaders() {
    return requestHeaders;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Api sets up the request to the GloballyPaid API, this is main interface.
   *
   * @param request the (@link Request) object
   * @return the {@link Response} object
   * @throws GloballyPaidException in case of a network error
   */
  public Response api(final Request request) throws GloballyPaidException {
    final Request req = new Request();

    req.setMethod(request.getMethod());
    req.setBaseUri(request.getBaseUri());
    req.setEndpoint(API_BASE_URL.concat(version).concat(request.getEndpoint()));
    req.setBody(request.getBody());
    req.setOptions(request.getOptions());
    req.setNonZeroCheck(request.isNonZeroCheck());

    if (!Objects.isNull(this.getRequestHeaders()) && !this.getRequestHeaders().isEmpty()) {
      for (final Map.Entry<String, String> header : this.requestHeaders.entrySet()) {
        req.addHeader(header.getKey(), header.getValue());
      }
    }

    if (!Objects.isNull(request.getQueryParams()) && !request.getQueryParams().isEmpty()) {
      for (final Map.Entry<String, String> queryParam : request.getQueryParams().entrySet()) {
        req.addQueryParam(queryParam.getKey(), queryParam.getValue());
      }
    }

    return makeApiCall(req);
  }

  /**
   * Makes the call to the GloballyPaid API, override this method for testing.
   *
   * @param request the {@link Request} to make
   * @return the {@link Response} object
   * @throws GloballyPaidException in case of a network error
   */
  public Response makeApiCall(final Request request) throws GloballyPaidException {
    return client.api(request);
  }

  /**
   * URL-encode a string ID in url path formatting.
   *
   * @param id id
   * @return Url encoded string from id
   * @throws InvalidRequestException In case of an invalid Id
   */
  public static String urlEncodeId(String id) throws InvalidRequestException {
    if (id == null) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST,
          "Invalid null ID found for url path formatting. This can be because your string ID "
              + "argument to the API method is null, or the ID field in your object instance is null.",
          null,
          null);
    }

    try {
      return URLEncoder.encode(id, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST,
          "The character encoding UTF-8 is unknown while encoding ID for url path formatting!",
          null,
          null);
    }
  }
}
