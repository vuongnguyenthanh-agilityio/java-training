package com.agility.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IResourceRepository<T, I extends Serializable> extends MongoRepository<T, I> {
  Page<T> findAll(Query query, Pageable pageable);
  Page<T> findAll(TextCriteria criteria, Query query, Pageable pageable);
}
