package com.myretail.product.adaptor;

import com.myretail.product.connector.RedSkyConnector;
import org.springframework.stereotype.Component;

@Component
public class RedSkyAdaptor {

  private RedSkyConnector redSkyConnector;

  public RedSkyAdaptor(RedSkyConnector redSkyConnector){
    this.redSkyConnector=redSkyConnector;
  }
  public String retrieveProductName(Long productId){
    return redSkyConnector.retrieveProductName(productId);
  }
}
