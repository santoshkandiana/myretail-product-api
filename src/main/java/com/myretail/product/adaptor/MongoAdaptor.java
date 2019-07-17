package com.myretail.product.adaptor;


import com.myretail.product.connector.MongoConnector;
import com.myretail.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class MongoAdaptor {

  private MongoConnector mongoConnector;

  public MongoAdaptor(MongoConnector mongoConnector){
    this.mongoConnector=mongoConnector;
  }

  public Product retrieveProductDetails(Long productId) {
    return mongoConnector.retrieveProductDetails(productId);
  }

  public void saveProduct(Product product) {
     mongoConnector.saveProduct(product);
  }

  public Product updateProductPrice(Product product) {
    return mongoConnector.updateProductPrice(product);
  }
}
