package com.myretail.product.connector;

import com.myretail.product.exception.RecordNotFoundException;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MongoConnectorImplTest {

  private MongoConnectorImpl mongoConnectorImpl;

  @Mock
  Datastore datastore;

  @Mock
  Query query;

  @Mock
  FieldEnd fieldEnd;

  Long productId;


  @Before
  public void setUp() {
    initMocks(this);
    productId = 1111L;
    mongoConnectorImpl= new MongoConnectorImpl(datastore);
  }

  @Test
  public void retrieveProductDetailSuccess() {

    Product expectedProduct = Product.builder().productId(productId)
                                  .price(Price.builder().value(13.49).currencyCode("USD").build()).build();

    when(datastore.find(Product.class)).thenReturn(query);
    when(datastore.find(Product.class).filter(any(),any())).thenReturn(query);
    when(query.get()).thenReturn(expectedProduct);

    Product actualProduct = mongoConnectorImpl.retrieveProductDetails(productId);
    assertEquals(expectedProduct,actualProduct);

  }

  @Test(expected = RecordNotFoundException.class)
  public void retrieveProductDetailsException() {


    Product expectedProduct = null;

    when(datastore.find(Product.class)).thenReturn(query);
    when(datastore.find(Product.class).filter(any(),any())).thenReturn(query);
    when(query.get()).thenReturn(expectedProduct);
    mongoConnectorImpl.retrieveProductDetails(productId);

  }

  @Test
  public void saveProduct() {

    Product product = Product.builder().productId(productId)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();

    when(datastore.save(product)).thenReturn(null);
    mongoConnectorImpl.saveProduct(product);
    verify(datastore, times(1)).save(product);


  }
}