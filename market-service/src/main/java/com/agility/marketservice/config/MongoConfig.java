package com.agility.marketservice.config;

import com.agility.marketservice.repository.ResourceRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.agility.marketservice.repository",
    repositoryBaseClass = ResourceRepository.class)
public class MongoConfig extends AbstractMongoClientConfiguration {
  @Value("${spring.data.mongodb.host}")
  private String mongoHost;

  @Value("${spring.data.mongodb.port}")
  private String mongoPort;

  @Value("${spring.data.mongodb.database}")
  private String mongoDb;

  @Override
  protected String getDatabaseName() {
    return mongoDb;
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return super.getMappingBasePackages();
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }

  @Override
  public MongoClient mongoClient() {
    String uri = "mongodb://" + mongoHost + ":" + mongoPort + "/" + mongoDb;
    ConnectionString connectionString = new ConnectionString(uri);
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }
}
