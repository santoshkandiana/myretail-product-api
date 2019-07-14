package com.myretail.product.connector;

import com.myretail.product.exception.InvalidProductNameException;
import com.myretail.product.exception.RedSkyProductNameNotFoundException;
import com.myretail.product.model.redsky.Item;
import com.myretail.product.model.redsky.Product;
import com.myretail.product.model.redsky.ProductDescription;
import com.myretail.product.model.redsky.RedSkyProductDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RedSkyConnectorImplTest {

  RedSkyConnectorImpl redSkyConnectorImpl;

  private String redSkyUrl="https://redsky.target.com/v2/pdp/tcin/";

  @Mock
  RestTemplate restTemplate;

  private String testName;


  @Before
  public void setUp(){
    initMocks(this);
    testName = "testName";
    redSkyConnectorImpl =  new RedSkyConnectorImpl(restTemplate,redSkyUrl);

  }

  @Test(expected = InvalidProductNameException.class)
  public void testResponseForNullItemRetrieveProductName() {


    RedSkyProductDetails redSkyProductDetails =
        RedSkyProductDetails.builder()
          .product(
              Product.builder().item(null).build()
              ).build();

    Long productId = 1111L;

    when(restTemplate.getForEntity(redSkyUrl+productId, RedSkyProductDetails.class))
          .thenReturn(new ResponseEntity(redSkyProductDetails, HttpStatus.OK));


    redSkyConnectorImpl.retrieveProductName(productId);

  }


  @Test(expected = InvalidProductNameException.class)
  public void testResponseForNullProductRetrieveProductName() {


    RedSkyProductDetails redSkyProductDetails =
        RedSkyProductDetails.builder()
            .product(null).build();

    Long productId = 1111L;

    when(restTemplate.getForEntity(redSkyUrl+productId, RedSkyProductDetails.class))
        .thenReturn(new ResponseEntity(redSkyProductDetails, HttpStatus.OK));


    redSkyConnectorImpl.retrieveProductName(productId);

  }

  @Test(expected = InvalidProductNameException.class)
  public void testResponseForNullProductDescriptionRetrieveProductName() {


    RedSkyProductDetails redSkyProductDetails =
        RedSkyProductDetails.builder()
            .product(
                Product.builder().item(Item.builder().productDescription(null).build()).build()
            ).build();

    Long productId = 1111L;

    when(restTemplate.getForEntity(redSkyUrl+productId, RedSkyProductDetails.class))
        .thenReturn(new ResponseEntity(redSkyProductDetails, HttpStatus.OK));


    redSkyConnectorImpl.retrieveProductName(productId);

  }

  @Test
  public void testSuccessRetrieveProductName() {


    RedSkyProductDetails redSkyProductDetails =
        RedSkyProductDetails.builder()
            .product(
                Product.builder().item(
                    Item.builder().productDescription(
                        ProductDescription.builder().title(testName).build()).build()).build()
            ).build();


    Long productId = 1111L;

    when(restTemplate.getForEntity(redSkyUrl+productId, RedSkyProductDetails.class))
        .thenReturn(new ResponseEntity(redSkyProductDetails, HttpStatus.OK));


    String actualTitle = redSkyConnectorImpl.retrieveProductName(productId);

    assertEquals(testName,actualTitle);
  }

  @Test(expected = RedSkyProductNameNotFoundException.class)
  public void testFailureRetrieveProductName() {


        RedSkyProductDetails.builder()
            .product(
                Product.builder().item(
                    Item.builder().productDescription(
                        ProductDescription.builder().title(testName).build()).build()).build()
            ).build();


    Long productId = 1111L;

    when(restTemplate.getForEntity(redSkyUrl+productId, RedSkyProductDetails.class))
        .thenThrow(HttpClientErrorException.class);


    String actualTitle = redSkyConnectorImpl.retrieveProductName(productId);

    assertEquals(testName,actualTitle);
  }


}