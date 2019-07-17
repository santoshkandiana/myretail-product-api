package com.myretail.product.connector;

import com.myretail.product.model.Product;
import com.myretail.product.exception.RecordNotFoundException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MongoConnectorImpl implements MongoConnector {

  private Datastore datastore;

  private static final String PRODUCT_ID = "productId";

  @Autowired
  public  MongoConnectorImpl(Datastore datastore){
    this.datastore=datastore;
  }

  @Override
  public Product retrieveProductDetails (Long productId) {
    Product product = datastore
                         .find(Product.class)
                         .filter(PRODUCT_ID,productId)
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

  @Override
  public Product updateProductPrice(Product productToBeUpdated) {

    Query<Product> productQuery = this.datastore.createQuery(Product.class);

    productQuery.criteria(PRODUCT_ID).equal(productToBeUpdated.getProductId());
    UpdateOperations<Product> ops = this.datastore.createUpdateOperations(Product.class)
                                          .set("price",productToBeUpdated.getPrice())
                                          .set(PRODUCT_ID,productToBeUpdated.getProductId());
    UpdateResults updateResults = this.datastore.update(productQuery,ops);
    if(updateResults.getUpdatedCount()==0){
      throw new RecordNotFoundException("Update operation failed as product Id doesn't exist");
    }else return productToBeUpdated;

  }
}
