package com.example.demo.DataBase.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_menu")
public class Menu {

  @Id
  @Column(name = "menu_name", nullable = false, updatable = false)
  private String menuName;

  /** 主類別 <沒有主類別就是頂層> **/
  @Column(name = "main_menu_name")
  private String mainMenuName;

  /** 父類別 <該類別的上一層類別> **/
  @Column(name = "parent_menu_name")
  private String parentMenuName;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parentMenuName", fetch = FetchType.LAZY)
  @OrderBy("sort")
  private List<Menu> childMenus = new ArrayList<>();

  @Column(name = "level")
  private Integer level;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;

  @Column(name = "sort")
  private Integer sort;

}
