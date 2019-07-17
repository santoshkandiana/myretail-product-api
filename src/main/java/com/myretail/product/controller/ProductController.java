package com.myretail.product.controller;

import com.myretail.product.model.*;
import com.myretail.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myretail/v1")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @ApiOperation(
        value = "GET Product Details",
        notes = "Gets product details like name, price details",
        response = ProductGetResponse.class
    )
    @ApiResponses({
        @ApiResponse(code=200, message = "retrieve successful"),
        @ApiResponse(code=400, message = "bad request"),
        @ApiResponse(code=404, message = "Product Name does not exist in redsky"),
        @ApiResponse(code=404, message = "Product Not found for the given productId"),

    })
    @GetMapping(value = "/products/{productId}")
    public ProductGetResponse getProduct(@PathVariable Long productId){
        return ProductGetResponse
                     .builder()
                     .product(productService.getProduct(productId))
                     .returnDetails(ReturnDetails
                                      .builder()
                                      .code(0)
                                      .message("Retrieve Successful")
                                      .source("myretail-product-api")
                                    .build())
                     .build();
    }


    @ApiOperation(
        value = "PUT Product Details",
        notes = "Update  product price details to database"
    )
    @ApiResponses({
        @ApiResponse(code=200, message = "Post successful"),
        @ApiResponse(code=400, message = "bad request"),
        @ApiResponse(code=404, message = "Product Not found for the given productId"),

    })

    @PutMapping(value = "/products/{productId}")
    public ProductPutResponse updateProduct(@RequestBody Price price ,
                                            @PathVariable Long productId){


        return ProductPutResponse.builder()
            .product(productService.updateProductPrice(Product.builder().productId(productId).price(price).build()))
            .returnDetails(ReturnDetails
                .builder()
                .code(0)
                .message("Update Successful")
                .source("myretail-product-api")
                .build())
            .build();

    }


    @ApiOperation(
        value = "POST Product Details",
        notes = "Post a product to database"
    )
    @ApiResponses({
        @ApiResponse(code=200, message = "Post successful"),
        @ApiResponse(code=400, message = "bad request")
    })
    @PostMapping(value = "/products")
    public void saveProduct(@RequestBody Product product ){

        productService.saveProduct(product);

    }



}
