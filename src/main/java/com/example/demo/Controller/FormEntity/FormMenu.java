package com.example.demo.Controller.FormEntity;

import com.example.demo.DataBase.Entity.Menu;

import lombok.Data;

@Data
public class FormMenu {

  private String menuName;

  private String mainMenuName;

  private String parentMenuName;

  private Integer level;

  private String name;

  private String url;

  private Integer sort;

  public Menu getMenu() {
    Menu menu = new Menu();
    menu.setMenuName(menuName);
    menu.setMainMenuName(mainMenuName);
    menu.setParentMenuName(parentMenuName);
    menu.setLevel(level);
    menu.setName(name);
    menu.setUrl(url);
    menu.setSort(sort);
    return menu;
  }

}
