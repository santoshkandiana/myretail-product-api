package com.myretail.product.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {

  private Double value;
  private String currencyCode;

}
