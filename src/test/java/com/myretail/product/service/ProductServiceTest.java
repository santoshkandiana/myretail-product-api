package com.myretail.product.service;

import com.myretail.product.adaptor.MongoAdaptor;
import com.myretail.product.adaptor.RedSkyAdaptor;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {

  @Mock
  private MongoAdaptor mongoAdaptor;

  @Mock
  private RedSkyAdaptor redSkyAdaptor;

  private Product expectedProduct;

  private Long productId =111l;

  private ProductService productService;

  @Before
  public void setUp(){
    initMocks(this);
    expectedProduct = Product.builder().productId(productId)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();
    productService = new ProductService(mongoAdaptor,redSkyAdaptor);
  }

  @Test
  public void testGetProduct() {

    //Arrange
    Price expectedPrice = Price.builder().value(13.49).currencyCode("USD").build();
    when(redSkyAdaptor.retrieveProductName(productId)).thenReturn("testName");
    when(mongoAdaptor.retrieveProductDetails(productId)).thenReturn(expectedProduct);

    //Act
    Product actualProduct = productService.getProduct(productId);

    //Assert
    assertEquals("testName" , actualProduct.getProductName());
    assertEquals(expectedPrice.toString() , actualProduct.getPrice().toString());
    assertEquals(productId , actualProduct.getProductId());

  }

  @Test
  public void testSaveProduct() {

    doNothing().when(mongoAdaptor).saveProduct(expectedProduct);
    productService.saveProduct(expectedProduct);
    verify(mongoAdaptor, times(1)).saveProduct(expectedProduct);

  }

  @Test
  public void testUpdateProduct() {

    Price expectedPrice = Price.builder().value(13.49).currencyCode("USD").build();
    when(mongoAdaptor.updateProductPrice(any())).thenReturn(expectedProduct);
    productService.updateProductPrice(expectedProduct);
    verify(mongoAdaptor, times(1)).updateProductPrice(any());
    assertEquals(expectedPrice.toString() , expectedProduct.getPrice().toString());


  }


}