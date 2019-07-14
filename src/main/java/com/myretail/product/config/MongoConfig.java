package com.myretail.product.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MongoConfig {

  @Bean
  MongoClientURI mongoClientURI(
      @Value("${spring.data.mongodb.host}") String host,
      @Value("${spring.data.mongodb.database}") String database,
      @Value("${spring.data.mongodb.username}") String username,
      @Value("${spring.data.mongodb.password}") String password,
      @Value("${spring.data.mongodb.retryWrites}") String retryWrites,
      @Value("${spring.data.mongodb.w}") String w,
      @Value("${spring.data.mongodb.authMechanism}") String authMechanism
      ){
    return new MongoClientURI("mongodb+srv://"+ username +":" + password +"@"+ host+"/"+ database+"?authMechanism="+authMechanism+"&retryWrites="+retryWrites+"&w="+w);

  }


  @Bean
  MongoClient mongoClient(MongoClientURI mongoClientURI){
    return new MongoClient(mongoClientURI);
  }

  @Bean
  MongoDatabase mongoDatabase(MongoClient mongoClient,
                              @Value("${spring.data.mongodb.database}") String database){
    return  mongoClient.getDatabase(database);
  }

  @Bean
  Morphia morphia(){
    Morphia morphia = new Morphia();
    morphia.mapPackage("com.myretail.product.model");
    return morphia;
  }

  @Bean
  Datastore datastore (Morphia morphia,
                       MongoClient mongoClient,
                       @Value("${spring.data.mongodb.database}") String database){
    Datastore datastore = morphia.createDatastore(mongoClient , database);
    datastore.ensureIndexes();
    return datastore;
  }


}
