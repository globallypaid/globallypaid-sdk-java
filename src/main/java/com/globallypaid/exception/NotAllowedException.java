package com.globallypaid.exception;

public class NotAllowedException extends GloballyPaidException {

  public NotAllowedException(Integer code, String message) {
    super(code, message);
  }
}
