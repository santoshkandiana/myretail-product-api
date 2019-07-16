package com.myretail.product.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.mongodb.morphia.annotations.Id;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @Id
    private Long productId;
    private String productName;
    private Price price;
}
