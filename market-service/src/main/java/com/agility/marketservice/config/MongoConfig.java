package com.agility.marketservice.config;

import com.agility.marketservice.repository.ResourceRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.agility.marketservice.repository",
    repositoryBaseClass = ResourceRepository.class)
public class MongoConfig extends AbstractMongoClientConfiguration {
  @Override
  protected String getDatabaseName() {
    return "markets";
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }
}
