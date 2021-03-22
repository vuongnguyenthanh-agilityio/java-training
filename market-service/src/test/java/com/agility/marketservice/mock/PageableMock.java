package com.agility.marketservice.mock;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Accessors(chain = true)
public class PageableMock implements Pageable {
  private int itemsPerPage;
  private int pageSize;

  @Override
  public int getPageNumber() {
    return this.itemsPerPage;
  }

  @Override
  public int getPageSize() {
    return this.pageSize;
  }

  @Override
  public long getOffset() {
    return 0;
  }

  @Override
  public Sort getSort() {
    return null;
  }

  @Override
  public Pageable next() {
    return null;
  }

  @Override
  public Pageable previousOrFirst() {
    return null;
  }

  @Override
  public Pageable first() {
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }
}
