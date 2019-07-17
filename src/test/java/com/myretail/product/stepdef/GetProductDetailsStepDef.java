package com.myretail.product.stepdef;

import com.myretail.product.MyretailProductApiApplication;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.model.ProductGetResponse;
import com.myretail.product.model.ReturnDetails;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = MyretailProductApiApplication.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("ci")
@Slf4j
public class GetProductDetailsStepDef {

  @Autowired
  private Datastore datastore;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Value("${redsky.uri}")
  private String redskyuri;


  @LocalServerPort
  private Integer port;

  private ResponseEntity<ProductGetResponse> responseEntity;

  private Product product;

  @Before
  public void setUp(){
    product =Product.builder().productId(13860428L)
        .price(Price.builder().value(13.49).currencyCode("USD").build()).build();

  }

  @After
  public void tearDown(){
    Query<Product> cleanUpCollectionQuery = datastore.createQuery(Product.class).disableValidation();
    datastore.delete(cleanUpCollectionQuery);
  }

  @Given("^for a productId and existing product name$")
  public void forAProductIdAndExistingProductName() {
    //Setting up data for the test case automation
    datastore.save(product);
  }

  @When("^the client getProductAPI$")
  public void theClientGetProductAPI() {
    String url= "http://localhost:" + port + "/myretail/v1/products/"+product.getProductId();
    responseEntity = testRestTemplate.getForEntity(url, ProductGetResponse.class);

  }

  @Then("^the client receives status code of SUCCESS$")
  public void theClientReceivesStatusCodeOfSUCCESS() {
    Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

  }

  @And("^the client receives product details$")
  public void theClientReceivesProductDetails() {
    Assert.assertEquals(product.getPrice().toString(),responseEntity.getBody().getProduct().getPrice().toString());
    Assert.assertEquals(product.getProductId(),responseEntity.getBody().getProduct().getProductId());
    Assert.assertNotNull(responseEntity.getBody().getProduct().getProductName());

  }

  @Given("^for a productId which does not exist$")
  public void forAProductIdWhichDoesNotExist() {
    if(datastore.find(Product.class).field("productId").equal(product.getProductId()).get()!=null){
      Assert.fail("Product ID exists , Fail the scenario");
    }
  }

  @Then("^the client receives status code of NOT FOUND$")
  public void theClientReceivesStatusCodeOfNOTFOUND() {
    Assert.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

  }

  @And("^the client receives response with no product details and failure response metadata$")
  public void theClientReceivesResponseWithNoProductDetailsAndFailureResponseMetadata() {
    Assert.assertNull(responseEntity.getBody().getProduct());
    Assert.assertEquals(ReturnDetails
                            .builder()
                              .code(404)
                              .message("Product Not found for the given productId")
                              .source("myretail-product-api")
                            .build().toString()
             ,responseEntity.getBody().getReturnDetails().toString());

  }

  @Given("^for a valid productId and product name does not exist$")
  public void forAValidProductIdAndProductNameDoesNotExist() {
    //Setting up data for the test case automation
    datastore.save(product);
    log.info("redskyuri value" + redskyuri);
    product.setProductId(11111111L);
    ResponseEntity<Object> redSkyResponseEntity = testRestTemplate.getForEntity(redskyuri+product.getProductId(),Object.class);
    if(redSkyResponseEntity.getStatusCode()!= HttpStatus.NOT_FOUND){
      Assert.fail("Since the product ID is existing in redsky and hence failing the scenario");
    }
  }


  @And("^the client receives response with no product name and failure response metadata$")
  public void theClientReceivesResponseWithNoProductNameAndFailureResponseMetadata() {
    Assert.assertNull(responseEntity.getBody().getProduct());
    Assert.assertEquals(ReturnDetails
            .builder()
            .code(404)
            .message("Product Name does not exist in redsky")
            .source("myretail-product-api")
            .build().toString()
        ,responseEntity.getBody().getReturnDetails().toString());
  }
}
