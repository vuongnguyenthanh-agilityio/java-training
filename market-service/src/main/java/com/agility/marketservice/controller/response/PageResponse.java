package com.agility.marketservice.controller.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageResponse<T> {
  private int totalPages;
  private long totalItems;
  private int currentPage;
  private boolean first;
  private boolean last;
  private int itemsPerPage;
  private int pageSize;

  private List<T> items;

  public void setPageResponse(Page pg, List<T> items) {
    this.first = pg.isFirst();
    this.last = pg.isLast();
    this.currentPage = pg.getNumber() + 1;
    this.pageSize = pg.getSize();
    this.totalPages = pg.getTotalPages();
    this.totalItems = pg.getTotalElements();
    this.itemsPerPage = pg.getNumberOfElements();
    this.items = items;
  }
}
