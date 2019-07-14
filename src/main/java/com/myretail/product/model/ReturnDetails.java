package com.myretail.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReturnDetails {
  private Integer code;
  private String message;
  private String source;
}
