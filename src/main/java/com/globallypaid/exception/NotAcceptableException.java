package com.globallypaid.exception;

public class NotAcceptableException extends GloballyPaidException {

  public NotAcceptableException(Integer code, String message) {
    super(code, message);
  }
}
