package com.myretail.product.model;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutResponse {
  private Product product;
  private ReturnDetails returnDetails;
}
