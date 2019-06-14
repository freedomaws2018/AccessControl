package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FormDataTables {

  private Integer draw;

  private Integer start;

  private Integer length;

  /** 個別欄位條件 **/
  private List<Column> columns = new ArrayList<>();

  /** 搜尋條件 **/
  private Search search;

  /** 排序 **/
  private List<Order> order = new ArrayList<>();

  @Data
  public class Search {
    private String value;
    private Boolean regex;
  }

  @Data
  public class Order {
    private Integer column;
    private String dir;
  }

  @Data
  public class Column {
    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private List<Search> search;
  }

}
