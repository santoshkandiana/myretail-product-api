package com.myretail.product.adaptor;

import com.myretail.product.connector.MongoConnector;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MongoAdaptorTest {

  @Mock
  MongoConnector mongoConnector;

  Long productId;

  private Product product;


  @Before
  public void setUp(){
    initMocks(this);
  }

  @Test
  public void testRetrieveProductDetails() {

    product = Product.builder().productId(productId)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();


    when(mongoConnector.retrieveProductDetails(productId)).thenReturn(product);
    Product actualProduct = mongoConnector.retrieveProductDetails(productId);
    assertEquals(product,actualProduct);

  }

  @Test
  public void testSaveProduct() {

    product = Product.builder().productId(productId)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();


    doNothing().when(mongoConnector).saveProduct(product);
    mongoConnector.saveProduct(product);
    verify(mongoConnector, times(1)).saveProduct(product);


  }
}