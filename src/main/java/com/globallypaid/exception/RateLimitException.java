package com.globallypaid.exception;

public class RateLimitException extends GloballyPaidException {

  public RateLimitException(Integer code, String message, Throwable e) {
    super(code, message, e);
  }
}
