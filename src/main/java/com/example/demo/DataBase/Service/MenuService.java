package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Repository.MenuRepository;
import com.example.demo.DataBase.Repository.MenuTemporaryRepository;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuTemporaryRepository menuTemporaryRepository;

  public List<Menu> getLevelOne() {
    List<Menu> menus = menuRepository.findByLevel(1);
    return menus;
  }

}
