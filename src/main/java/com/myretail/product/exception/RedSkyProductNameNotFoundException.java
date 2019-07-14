package com.myretail.product.exception;

public class RedSkyProductNameNotFoundException extends RuntimeException {

  public RedSkyProductNameNotFoundException() {
    super();
  }

  public RedSkyProductNameNotFoundException(String message) {
    super(message);
  }

  public RedSkyProductNameNotFoundException(String message,Throwable throwable) {
    super(message,throwable);
  }

  public RedSkyProductNameNotFoundException(Throwable throwable) {
    super(throwable);
  }
}
