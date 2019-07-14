package com.myretail.product.exception;

public class RecordNotFoundException extends RuntimeException {

  public RecordNotFoundException() {
    super();
  }

  public RecordNotFoundException(String message) {
    super(message);
  }

  public RecordNotFoundException(String message,Throwable throwable) {
    super(message,throwable);
  }

  public RecordNotFoundException(Throwable throwable) {
    super(throwable);
  }

}
