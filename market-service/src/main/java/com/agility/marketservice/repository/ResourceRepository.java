package com.agility.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.List;

public class ResourceRepository<T, I extends Serializable> extends SimpleMongoRepository<T, I> implements IResourceRepository<T, I>{
  private MongoOperations mongoOperations;
  private MongoEntityInformation metadata;
  public ResourceRepository(MongoEntityInformation metadata, MongoOperations mongoOperations) {
    super(metadata, mongoOperations);
    this.metadata = metadata;
    this.mongoOperations = mongoOperations;
  }

  @Override
  public Page<T> findAll(Query query, Pageable pageable) {
    long total = mongoOperations.count(query, metadata.getJavaType(), metadata.getCollectionName());
    List<T> content = mongoOperations.find(query.with(pageable), metadata.getJavaType(), metadata.getCollectionName());

    return new PageImpl<T>(content, pageable, total);
  }

  /**
   * TODO: Don't implement yet
   * Need to find the solutions
   * @param criteria
   * @param query
   * @param pageable
   * @return
   */
  @Override
  public Page<T> findAll(TextCriteria criteria, Query query, Pageable pageable) {

    return null;
  }
}
