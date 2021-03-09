package com.agility.marketservice.repository;

import com.agility.marketservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends IResourceRepository<Product, String> {
  Page<Product> findAllBy(TextCriteria criteria, Pageable pageable);
}
