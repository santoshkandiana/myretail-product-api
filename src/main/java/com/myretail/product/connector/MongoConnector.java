package com.myretail.product.connector;

import com.myretail.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface MongoConnector {
  Product retrieveProductDetails(Long productId);

  void saveProduct(Product product);

  Product updateProductPrice(Product product);
}
