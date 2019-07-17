Feature: Update ProductDetails price for a given productId

  Scenario: Success:For an existing product update price details
    Given an existing productId
    When the client invokes putProductAPI
    Then the client receives status code of SUCCESS for PUT operation
    And the price details are updated

  Scenario: Failure:For a non existing product update price details
    Given for a productId which does not exist for put operation
    When the client invokes putProductAPI
    Then the client receives status code of NOT FOUND for put operation
    And the client receives response with no product details and failure response metadata for update

