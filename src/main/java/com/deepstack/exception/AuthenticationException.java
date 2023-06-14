package com.deepstack.exception;

public class AuthenticationException extends GloballyPaidException {
  private static final long serialVersionUID = 2L;

  public AuthenticationException(Integer code, String message) {
    super(code, message);
  }

  public AuthenticationException(String message) {
    super(message);
  }
}
