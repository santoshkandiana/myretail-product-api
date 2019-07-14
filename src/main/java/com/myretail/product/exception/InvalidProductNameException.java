package com.myretail.product.exception;

public class InvalidProductNameException extends RuntimeException{
  public InvalidProductNameException() {
    super();
  }

  public InvalidProductNameException(String message) {
    super(message);
  }

  public InvalidProductNameException(String message,Throwable throwable) {
    super(message,throwable);
  }

  public InvalidProductNameException(Throwable throwable) {
    super(throwable);
  }
}

