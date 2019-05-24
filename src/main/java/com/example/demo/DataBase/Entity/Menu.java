package com.example.demo.DataBase.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "tbl_menu")
public class Menu {

  @Id
  @Column(name = "menu_name", nullable = false, updatable = false)
  private String menuName;

//  /** 主類別 <沒有主類別就是頂層> **/
//  @ToString.Exclude
//  @Transient
//  private Menu mainMenu = null;
//  @Column(name = "main_menu_name")
//  private String mainMenuName;
//
//  /** 父類別 <該類別的上一層類別> **/
//  @ToString.Exclude
//  @Transient
//  private Menu parentMenu = null;
//  @Column(name = "parent_menu_name")
//  private String parentMenuName;
//
//  /** 子類別<該類別的下一層類別> **/
//  @ToString.Exclude
//  @Transient
//  private List<Menu> childMenus = new ArrayList<>();

  /** 主類別 <沒有主類別就是頂層> **/
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "main_menu", referencedColumnName = "menu_name")
  private Menu mainMenu = null;

  /** 父類別 <該類別的上一層類別> **/
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_menu", referencedColumnName = "menu_name")
  private Menu parentMenu = null;
//  @Column(name = "parent_class")
//  private String parentClass;

  @Transient
  @ToString.Exclude
  private List<Menu> childMenus = new ArrayList<>();

//
//  /** 子類別<該類別的下一層類別> **/
////  @Transient
////  private List<Menu> childMenus = new ArrayList<>();
////  @OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL }, mappedBy = "parentClass", fetch = FetchType.LAZY )
//  @Fetch(FetchMode.SUBSELECT)
//  @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parentClass", fetch = FetchType.LAZY)
//  @OrderBy("sort")
//  private List<Menu> childMenus = new ArrayList<>();

  @Column(name = "level")
  private Integer level;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;

  @Column(name = "sort")
  private Integer sort;

}
