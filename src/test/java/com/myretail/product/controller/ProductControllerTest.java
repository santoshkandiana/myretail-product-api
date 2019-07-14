package com.myretail.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.exception.RestControllerAdvise;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.model.ProductGetResponse;
import com.myretail.product.model.ReturnDetails;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProductControllerTest {

  @Mock
  ProductService productService;

  private MockMvc mockMvc;

  private Long productId = 1111L;

  private ObjectMapper objectMapper;

  @Before
  public void setUp(){
    initMocks(this);
    objectMapper = new ObjectMapper();
    this.mockMvc = standaloneSetup(new ProductController(productService))
                     .setControllerAdvice(new RestControllerAdvise())
                     .build();
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
         get("/myretail/v1/products/"+productId))
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
        post("/myretail/v1/products/")
          .content(objectMapper.writeValueAsString(product))
          .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

    //Assert
    verify(productService, times(1)).saveProduct(any());
  }
}