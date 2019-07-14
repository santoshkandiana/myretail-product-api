package com.myretail.product.connector;

import com.myretail.product.model.Product;
import com.myretail.product.exception.RecordNotFoundException;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MongoConnectorImpl implements MongoConnector {

  private Datastore datastore;

  @Autowired
  public  MongoConnectorImpl(Datastore datastore){
    this.datastore=datastore;
  }

  @Override
  public Product retrieveProductDetails (Long productId) {
    Product product = datastore
                         .find(Product.class)
                         .filter("productId",productId)
                         .get();
    if(ObjectUtils.isEmpty(product)){
      throw new RecordNotFoundException("Product Not found for the given productId");
    }
    return product;

  }

  @Override
  public void saveProduct(Product product) {
    datastore.save(product);
  }
}
