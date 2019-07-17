package com.myretail.product.stepdef;

import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.model.ProductPutResponse;
import com.myretail.product.model.ReturnDetails;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PutProductDetailsStepDef extends AbstractStepDef {

  @Autowired
  private Datastore datastore;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private Integer port;

  private Product product;

  private Product productInRequest;


  private ResponseEntity<ProductPutResponse> responseEntity;

  @Before
  public void setUp(){

    product =Product.builder().productId(13860428L)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();
    productInRequest = Product.builder().productId(13860428L)
        .price(Price.builder().value(26.98).currencyCode("USD").build()).build();

  }

  @After
  public void tearDown(){
    Query<Product> cleanUpCollectionQuery = datastore.createQuery(Product.class).disableValidation();
    datastore.delete(cleanUpCollectionQuery);
  }

  @Given("^an existing productId$")
  public void anExistingProductId() {
    datastore.save(product);

  }

  @When("^the client invokes putProductAPI$")
  public void theClientInvokesPutProductAPI() {
    String url= "http://localhost:" + port + "/myretail/v1/products/"+product.getProductId();
    HttpEntity httpEntity =  new HttpEntity(productInRequest.getPrice());
    responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT,httpEntity,ProductPutResponse.class);

  }

  @Then("^the client receives status code of SUCCESS for PUT operation$")
  public void theClientReceivesStatusCodeOfSUCCESSForPUTOperation() {
    Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

  }

  @And("^the price details are updated$")
  public void thePriceDetailsAreUpdated() {
    Product productInMongo = datastore
        .find(Product.class)
        .filter("productId",productInRequest.getProductId())
        .get();

    Assert.assertEquals(productInRequest.getPrice().getValue(),productInMongo.getPrice().getValue());

  }

  @Given("^for a productId which does not exist for put operation$")
  public void forAProductIdWhichDoesNotExistForPutOperation() {

    if(datastore.find(Product.class).field("productId").equal(product.getProductId()).get()!=null){
      Assert.fail("Product ID exists , Fail the scenario");
    }

  }

  @Then("^the client receives status code of NOT FOUND for put operation$")
  public void theClientReceivesStatusCodeOfNOTFOUNDForPutOperation() {
    Assert.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

  }

  @And("^the client receives response with no product details and failure response metadata for update$")
  public void theClientReceivesResponseWithNoProductDetailsAndFailureResponseMetadata() {
    Assert.assertNull(responseEntity.getBody().getProduct());
    Assert.assertEquals(ReturnDetails
            .builder()
            .code(404)
            .message("Update operation failed as product Id doesn't exist")
            .source("myretail-product-api")
            .build().toString()
        ,responseEntity.getBody().getReturnDetails().toString());

  }

}
