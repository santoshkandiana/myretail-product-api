package com.myretail.product.adaptor;

import com.myretail.product.connector.RedSkyConnector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RedSkyAdaptorTest {

  @Mock
  RedSkyConnector redSkyConnector;

  private Long productId=111l;

  @Before
  public void setUp(){
    initMocks(this);
  }

  @Test
  public void retrieveProductName() {
    when(redSkyConnector.retrieveProductName(productId)).thenReturn("testName");
    String actualProductName = redSkyConnector.retrieveProductName(productId);
    assertEquals("testName",actualProductName);
  }
}