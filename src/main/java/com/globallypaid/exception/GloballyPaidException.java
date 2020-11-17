package com.globallypaid.exception;

import com.globallypaid.util.JsonUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.http.util.TextUtils;

@Getter
public abstract class GloballyPaidException extends Exception {
  private static final long serialVersionUID = 2L;

  /** The error resource returned by GloballyPaid's API that caused the exception. */
  //  @Setter
  private Map<String, Object> globallyPaidError = new HashMap<>();

  private Integer code;
  private String message;

  public GloballyPaidException(final Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public GloballyPaidException(String message, Throwable e) {
    super(message, e);
    this.message = message;
  }

  public GloballyPaidException(String message) {
    super(message);
    this.message = message;
  }

  protected GloballyPaidException(Integer code, String message, Throwable e) {
    super(message, e);
    this.code = code;
    this.message = message;
  }

  public Map<String, Object> setGloballyPaidError(String globallyPaidError) throws IOException {
    this.globallyPaidError =
        TextUtils.isBlank(globallyPaidError)
            ? new HashMap<>()
            : JsonUtils.convertFromJsonToObject(globallyPaidError, Map.class);
    return this.globallyPaidError;
  }

  //  /**
  //   * Returns a description of the exception, including the HTTP status code.
  //   *
  //   * @return a string representation of the exception.
  //   */
  //  @Override
  //  public String getMessage() {
  //    return String.format(
  //        "Status code: [%d]"
  //            + (TextUtils.isBlank(message) ? "" : ", message: [%s]")
  //            + (TextUtils.isBlank(globallyPaidError) ? "" : ", API error: [%s]"),
  //        code,
  //        message,
  //        globallyPaidError);
  //  }
}
