package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Repository.MenuRepository;
import com.google.common.base.Functions;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Transactional
  public List<Menu> getAll() {
    List<Menu> allMenu = menuRepository.findAll();
    Map<String, Menu> allMenuMap = allMenu.stream()
        .collect(Collectors.toMap(menu -> menu.getMenuName(), Functions.identity()));
    allMenu.sort((m1, m2) -> m2.getLevel().compareTo(m1.getLevel()));
    allMenu.stream().forEach(menu -> {
      if (menu.getParentMenu() != null) {
        String menuName = menu.getParentMenu().getMenuName();
        if (allMenuMap.containsKey(menuName) && StringUtils.isNotBlank(menuName)) {
          allMenuMap.get(menuName).getChildMenus().add(menu);
        }
      }
    });

    allMenu = allMenuMap.entrySet().stream() //
    .filter(menu -> menu.getValue().getLevel() == 1)
    .map(menu ->{
      return menu.getValue();
    }).sorted().collect(Collectors.toList());
    System.err.println(allMenu);

//    allMenu = allMenu.stream().filter(menu -> menu.getLevel() == 1).collect(Collectors.toList());
//
//    Map<String, Menu> allMenuMap = allMenu.stream()
//        .collect(Collectors.toMap(menu -> menu.getMenuName(), Functions.identity()));
//    List<Menu> menuInLevel1 = allMenu.stream().filter(menu -> menu.getLevel() == 1).collect(Collectors.toList());
//    List<Menu> menuInLevel2 = allMenu.stream().filter(menu -> menu.getLevel() == 2).collect(Collectors.toList());
//    List<Menu> menuInLevel3 = allMenu.stream().filter(menu -> menu.getLevel() == 3).collect(Collectors.toList());
//    List<Menu> menuInLevel4 = allMenu.stream().filter(menu -> menu.getLevel() == 4).collect(Collectors.toList());
//    menuInLevel3.forEach(menu3 -> {
//      if (allMenuMap.containsKey(menu3.getMenuName())) {
//        allMenuMap.get(menu3.getMenuName()).getChildMenus().add(menu3);
//      }
//    });
//    System.err.println(menuInLevel3);
//    menuInLevel2.forEach(menu2 -> {
//      if (allMenuMap.containsKey(menu2.getMenuName())) {
//        allMenuMap.get(menu2.getMenuName()).getChildMenus().add(menu2);
//      }
//    });
//    System.err.println(menuInLevel2);
//    menuInLevel1.forEach(menu1 -> {
//      if (allMenuMap.containsKey(menu1.getMenuName())) {
//        allMenuMap.get(menu1.getMenuName()).getChildMenus().add(menu1);
//
//      }
//    });
//    System.err.println(menuInLevel1);

    return allMenu;
  }

  public List<Menu> getLevelOne() {
    List<Menu> menus = menuRepository.findByLevel(1);
//    menus = getMenuEntity(menus);
    return menus;
  }

  public List<Menu> getMenuEntity(List<Menu> menus) {
//    menus.forEach(menu -> {
//      if (StringUtils.isNotBlank(menu.getMainMenuName())) {
//        Menu mainMenu = menuRepository.findById(menu.getMainMenuName()).orElse(null);
//        menu.setMainMenu(mainMenu);
//      }
//      if (StringUtils.isNotBlank(menu.getParentMenuName())) {
//        Menu parentMenu = menuRepository.findById(menu.getParentMenuName()).orElse(null);
//        menu.setParentMenu(parentMenu);
//      }
//      if (StringUtils.isBlank(menu.getUrl())) {
//        List<Menu> childMenus = menuRepository.findByParentMenuName(menu.getMenuName());
//        if (childMenus != null && !childMenus.isEmpty()) {
//          menu.setChildMenus(childMenus);
//          childMenus = getMenuEntity(childMenus);
//        }
//      }
//    });
    return menus;
  }

}
