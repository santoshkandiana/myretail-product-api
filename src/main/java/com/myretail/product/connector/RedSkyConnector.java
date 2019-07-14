package com.myretail.product.connector;

import org.springframework.stereotype.Component;

@Component
public interface RedSkyConnector {
  public String retrieveProductName(Long productId);

}
