package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Repository.MenuRepository;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  public List<Menu> getAll(){
    return menuRepository.findAll();
  }

  public List<Menu> getLevelOne(){
    return menuRepository.findByLevel(1);
  }

}
