package com.deepstack.exception;

public class ForbiddenException extends GloballyPaidException {

  public ForbiddenException(Integer code, String message, Throwable e) {
    super(code, message, e);
  }
}
