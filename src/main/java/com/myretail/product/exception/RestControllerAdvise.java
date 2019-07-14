package com.myretail.product.exception;

import com.myretail.product.model.ProductGetResponse;
import com.myretail.product.model.ReturnDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@NoArgsConstructor
public class RestControllerAdvise {


  @ExceptionHandler(RedSkyProductNameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProductGetResponse handleProductNameNotFoundException(RedSkyProductNameNotFoundException ex){
    return getErrorResponse(ex,404);
  }

  @ExceptionHandler(InvalidProductNameException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProductGetResponse handleInvalidProductNameException(InvalidProductNameException ex){
    return getErrorResponse(ex,404);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProductGetResponse handleRecordNotFoundException(RecordNotFoundException ex){
    return getErrorResponse(ex,404);
  }



  private ProductGetResponse getErrorResponse(RuntimeException exception, int code) {
    return ProductGetResponse.builder()
                          .returnDetails(ReturnDetails.builder()
                                            .source("myretail-product-api")
                                            .code(code)
                                            .message(exception.getMessage()).build()).build();
  }


  public ProductGetResponse handleWhenNoProductIdNotFound(RecordNotFoundException e){
    return getErrorResponse(e,404);
  }


}
