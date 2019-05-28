package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormMenu;
import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

  @Autowired
  private MenuService menuService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model) {
    model = new ModelAndView("layout/menu/u_menu");
    List<Menu> menus = menuService.getAllWithChild();
    model.addObject("menus", menus);
    return model;
  }

  @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> save(FormMenu form) {
    Menu menu = menuService.save(form.getMenu());
    Map<String , Object> result = new HashMap<>();
    result.put("menu", menu);
    result.put("status", "success");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> delete(String menuName){
    menuService.deleteMenuWithMenuName(menuName);
    Map<String , Object> result = new HashMap<>();
    result.put("status", "success");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
