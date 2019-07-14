package com.myretail.product.connector;

import com.myretail.product.exception.InvalidProductNameException;
import com.myretail.product.exception.RedSkyProductNameNotFoundException;
import com.myretail.product.model.redsky.Item;
import com.myretail.product.model.redsky.Product;
import com.myretail.product.model.redsky.ProductDescription;
import com.myretail.product.model.redsky.RedSkyProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RedSkyConnectorImpl implements RedSkyConnector {

  private RestTemplate restTemplate;
  private String redskyuri;

  @Autowired
  public RedSkyConnectorImpl(RestTemplate restTemplate,
                             @Value("${redsky.uri}") String redskyuri) {
    this.restTemplate = restTemplate;
    this.redskyuri = redskyuri;
  }

  @Override
  public String retrieveProductName(Long productId) {

    ResponseEntity<RedSkyProductDetails> responseEntity=null;
    try {
      responseEntity = restTemplate.getForEntity(redskyuri + productId, RedSkyProductDetails.class);
    }catch(HttpClientErrorException httpClientErrorException){
        throw new RedSkyProductNameNotFoundException("Product Name does not exist in redsky");
    }

    return Optional.ofNullable(responseEntity.getBody()).map(
        RedSkyProductDetails::getProduct
    ).map(
        Product::getItem
    ).map(
        Item::getProductDescription
    ).map(
        ProductDescription::getTitle)
     .orElseThrow(InvalidProductNameException::new);




  }
}
