package com.myretail.product.model.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RedSkyProductDetails {

  @JsonProperty("product")
  private Product product;


}