package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

  @Autowired
  private MenuService menuService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/menu/l_menu");
    List<Menu> menus = menuService.getAllWithChild();
    model.addObject("menus", menus);
    System.err.println(menus);
    return model;
  }

  @GetMapping(value = "/edit")
  private ModelAndView edit(ModelAndView model) {
    model = new ModelAndView("layout/menu/u_menu");
    return model;
  }

}
