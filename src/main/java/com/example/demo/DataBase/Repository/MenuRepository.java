package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

  List<Menu> findByLevel(Integer level);

//  @Query(value = "SELECT * FROM tbl_menu WHERE level > 1 AND parent_menu = ?1 ;", nativeQuery = true)
  /** SELECT * FROM tbl_menu WHERE level = ? AND parent_menu_name = ? **/
//  List<Menu> findByLevelEqualsAndParentMenuName(Integer level , String parentMenuName);
//
//  List<Menu> findByParentMenuName(String parentMenuName);

}
