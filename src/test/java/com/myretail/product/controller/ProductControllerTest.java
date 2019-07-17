package com.myretail.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.exception.RestControllerAdvise;
import com.myretail.product.model.*;
import com.myretail.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProductControllerTest {

  @Mock
  ProductService productService;

  private MockMvc mockMvc;

  private Long productId = 1111L;

  private ObjectMapper objectMapper;

  private String uri;


  @Before
  public void setUp(){
    initMocks(this);
    objectMapper = new ObjectMapper();
    this.mockMvc = standaloneSetup(new ProductController(productService))
                     .setControllerAdvice(new RestControllerAdvise())
                     .build();
    uri = "/myretail/v1/products/";
  }



  @Test
  public void testGetProductSuccess() throws Exception {

    //Arrange
    Product product = Product.builder()
                                .productId(productId)
                                .price(Price.builder().value(13.49).currencyCode("USD").build())
                             .build();
    ProductGetResponse expectedProductGetResponse = ProductGetResponse.builder()
                                                 .product(product)
                                                 .returnDetails(ReturnDetails.builder()
                                                     .code(0)
                                                     .message("Retrieve Successful")
                                                     .source("myretail-product-api").build())
                                                 .build();


    when(productService.getProduct(productId)).thenReturn(product);

    //Act
    MvcResult mvcResult = mockMvc.perform(
         get(uri +productId))
        .andExpect(status().isOk())
        .andReturn();

    ProductGetResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductGetResponse.class);
    //Assert
    assertEquals(expectedProductGetResponse.toString(),actualResponse.toString());
    verify(productService, times(1)).getProduct(productId);

  }

  @Test
  public void testSaveProductSuccess() throws Exception {

    Product product = Product.builder()
        .productId(productId)
        .price(Price.builder().value(13.49).currencyCode("USD").build())
        .build();

    doNothing().when(productService).saveProduct(product);

    //Act
    mockMvc.perform(
        post(uri)
          .content(objectMapper.writeValueAsString(product))
          .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

    //Assert
    verify(productService, times(1)).saveProduct(any());
  }

  @Test
  public void testUpdateProductSuccess() throws Exception {

    Price price = Price.builder().value(13.49).currencyCode("USD").build();

    Product expectedProduct = Product.builder()
        .productId(productId)
        .price(Price.builder().value(26.49).currencyCode("USD").build())
        .build();

    ProductPutResponse expectedProductPutResponse = ProductPutResponse.builder()
        .product(expectedProduct)
        .returnDetails(ReturnDetails.builder()
            .code(0)
            .message("Update Successful")
            .source("myretail-product-api").build())
        .build();

    when(productService.updateProductPrice(any())).thenReturn(expectedProduct);

    //Act
    MvcResult mvcResult=mockMvc.perform(
        put(uri+productId)
            .content(objectMapper.writeValueAsString(price))
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk())
        .andReturn();

    //Assert
    verify(productService, times(1)).updateProductPrice(any());
    ProductPutResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductPutResponse.class);
    //Assert
    assertEquals(expectedProductPutResponse.toString(),actualResponse.toString());
  }
}