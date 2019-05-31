package com.example.demo.DataBase.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Entity.MenuTemporary;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeMenu;
import com.example.demo.DataBase.Repository.MappingEmployeeMenuRepository;
import com.example.demo.DataBase.Repository.MenuRepository;
import com.example.demo.DataBase.Repository.MenuTemporaryRepository;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuTemporaryRepository menuTemporaryRepository;

  @Autowired
  private MappingEmployeeMenuRepository mappingEmployeeMenuRepository;

  public List<Menu> getAllByNameLike(String name) {
    return menuRepository.findByNameLike("%" + name + "%");
  }

  public Menu save(Menu menu) {
    return menuRepository.save(menu);
  }

  public void deleteMenuWithMenuName(String menuName) {
    menuRepository.deleteById(menuName);
  }

  public List<Menu> getAllWithChild() {
    List<Menu> menus = menuRepository.findAll();
    List<Menu> mv1s = menus.stream().filter(menu -> menu.getLevel() == 1).sorted(Comparator.comparing(Menu::getSort))
        .collect(Collectors.toList());
    List<Menu> mv2s = menus.stream().filter(menu -> menu.getLevel() == 2).sorted(Comparator.comparing(Menu::getSort))
        .collect(Collectors.toList());
    List<Menu> mv3s = menus.stream().filter(menu -> menu.getLevel() == 3).sorted(Comparator.comparing(Menu::getSort))
        .collect(Collectors.toList());
    List<Menu> mv4s = menus.stream().filter(menu -> menu.getLevel() == 4).sorted(Comparator.comparing(Menu::getSort))
        .collect(Collectors.toList());
    List<Menu> mv5s = menus.stream().filter(menu -> menu.getLevel() == 5).sorted(Comparator.comparing(Menu::getSort))
        .collect(Collectors.toList());
    mv5s.stream().forEach(mv5 -> {
      mv4s.stream().forEach(mv4 -> {
        if (mv5.getParentMenuName().equals(mv4.getMenuName())) {
          mv4.getChildMenus().add(mv5);
        }
      });
    });
    mv4s.stream().forEach(mv4 -> {
      mv3s.stream().forEach(mv3 -> {
        if (mv4.getParentMenuName().equals(mv3.getMenuName())) {
          mv3.getChildMenus().add(mv4);
        }
      });
    });
    mv3s.stream().forEach(mv3 -> {
      mv2s.stream().forEach(mv2 -> {
        if (mv3.getParentMenuName().equals(mv2.getMenuName())) {
          mv2.getChildMenus().add(mv3);
        }
      });
    });
    mv2s.stream().forEach(mv2 -> {
      mv1s.stream().forEach(mv1 -> {
        if (mv2.getParentMenuName().equals(mv1.getMenuName())) {
          mv1.getChildMenus().add(mv2);
        }
      });
    });

    return mv1s;
  }

  public List<Menu> getWithChildAndPermission(Long employeeId) {
    List<MappingEmployeeMenu> mappingEM = mappingEmployeeMenuRepository.findByEmployeeIdAndIsUseTrue(employeeId);
    List<String> employeeHaveMenuList = mappingEM.stream().map(MappingEmployeeMenu::getMenuName)
        .collect(Collectors.toList());
    List<Menu> menus = menuRepository.findByMenuNameIn(employeeHaveMenuList);
    List<Menu> mv1s = menus.stream().filter(menu -> menu.getLevel() == 1).collect(Collectors.toList());
    List<Menu> mv2s = menus.stream().filter(menu -> menu.getLevel() == 2).collect(Collectors.toList());
    List<Menu> mv3s = menus.stream().filter(menu -> menu.getLevel() == 3).collect(Collectors.toList());
    List<Menu> mv4s = menus.stream().filter(menu -> menu.getLevel() == 4).collect(Collectors.toList());
    List<Menu> mv5s = menus.stream().filter(menu -> menu.getLevel() == 5).collect(Collectors.toList());
    mv5s.stream().forEach(mv5 -> {
      mv4s.stream().forEach(mv4 -> {
        if (mv5.getParentMenuName().equals(mv4.getMenuName())) {
          mv4.getChildMenus().add(mv5);
        }
      });
    });
    mv4s.stream().forEach(mv4 -> {
      mv3s.stream().forEach(mv3 -> {
        if (mv4.getParentMenuName().equals(mv3.getMenuName())) {
          mv3.getChildMenus().add(mv4);
        }
      });
    });
    mv3s.stream().forEach(mv3 -> {
      mv2s.stream().forEach(mv2 -> {
        if (mv3.getParentMenuName().equals(mv2.getMenuName())) {
          mv2.getChildMenus().add(mv3);
        }
      });
    });
    mv2s.stream().forEach(mv2 -> {
      mv1s.stream().forEach(mv1 -> {
        if (mv2.getParentMenuName().equals(mv1.getMenuName())) {
          mv1.getChildMenus().add(mv2);
        }
      });
    });

    return mv1s;
  }

  public List<Menu> getByEmployeeId(Long employeeId) {
    MenuTemporary temp = menuTemporaryRepository.getByEmployeeId(employeeId).orElse(null);
    if (temp != null) {
      Duration duration = Duration.between(LocalDateTime.now(), temp.getCreateDate());
      Long durMin = Math.abs(duration.toMinutes());
      if (durMin <= 86400) {
        return temp.getTemporary();
      }
    }
    // 以上判斷 有無暫存 與 暫存是否超過一天 若不符合 就重新組
    List<Menu> menu = this.getWithChildAndPermission(employeeId);

    temp = new MenuTemporary();
    temp.setEmployeeId(employeeId);
    temp.setTemporary(menu);
    temp.setCreateDate(LocalDateTime.now());
    menuTemporaryRepository.save(temp);

    return menu;
  }

}
