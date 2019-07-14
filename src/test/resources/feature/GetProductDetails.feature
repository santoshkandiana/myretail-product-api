Feature: Get ProductDetails for a given productId

  Scenario: Success:For an existing product returns product details with product name from an external API
    Given for a productId and existing product name
    When the client getProductAPI
    Then the client receives status code of SUCCESS
    And the client receives product details

  Scenario: Failure:For an non-existing product returns product details with product name from an external API
    Given for a productId which does not exist
    When the client getProductAPI
    Then the client receives status code of NOT FOUND
    And the client receives response with no product details and failure response metadata

  Scenario: Failure:For an existing product but non existent product name returns product details with product name from an external API
    Given for a valid productId and product name does not exist
    When the client getProductAPI
    Then the client receives status code of NOT FOUND
    And the client receives response with no product name and failure response metadata