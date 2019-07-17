package com.myretail.product.health;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthCheck implements HealthIndicator {

  private Datastore datastore;

  @Autowired
  public MongoHealthCheck (Datastore datastore){
    this.datastore=datastore;
  }

  @Override
  public Health health() {
    if (!check()) {
      return Health.down()
          .withDetail("Error Code", 404).build();
    }
    return Health.up().withDetail("Mongo Database is Up",200).build();
  }

  public Boolean check() {
    return datastore.getDB().getStats().ok();
  }
}
