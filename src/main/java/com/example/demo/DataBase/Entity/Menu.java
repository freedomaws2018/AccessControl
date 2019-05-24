package com.example.demo.DataBase.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_menu")
public class Menu {

  @Id
  @Column(name = "class_name", nullable = false, updatable = false)
  private String className;

  /** 主類別 <沒有主類別就是頂層> **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_class", referencedColumnName = "class_name")
  private Menu mainMenu = null;

  /** 父類別 <該類別的上一層類別> **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_class", referencedColumnName = "class_name")
  private Menu parentMenu = null;

  /** 子類別<該類別的下一層類別> **/
//  @OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL }, mappedBy = "parentClass", fetch = FetchType.LAZY )
//  @Fetch(FetchMode.SUBSELECT)
  @OneToMany( cascade = { CascadeType.ALL }, mappedBy = "parentMenu", fetch = FetchType.LAZY)
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
