package com.myretail.product.service;

import com.myretail.product.adaptor.MongoAdaptor;
import com.myretail.product.model.Product;
import com.myretail.product.adaptor.RedSkyAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private MongoAdaptor mongoAdaptor;
  private RedSkyAdaptor redSkyAdaptor;

  @Autowired
  public ProductService(MongoAdaptor mongoAdaptor,
                        RedSkyAdaptor redSkyAdaptor){
    this.mongoAdaptor=mongoAdaptor;
    this.redSkyAdaptor=redSkyAdaptor;
  }

  public Product getProduct(Long productId){

    return Product.builder()
        .productName(redSkyAdaptor.retrieveProductName(productId))
        .productId(productId)
        .price(mongoAdaptor.retrieveProductDetails(productId).getPrice())
        .build();

  }

  public void saveProduct(Product product) {
     mongoAdaptor.saveProduct(product);
  }

  public Product updateProductPrice(Product product) {
      return mongoAdaptor.updateProductPrice(product);

  }
}
