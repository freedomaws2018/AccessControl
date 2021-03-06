package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

  List<Menu> findByLevel(Integer level);

  List<Menu> findByMenuNameIn(List<String> menuNames);

  List<Menu> findByNameLike(String name);

  Optional<Menu> findByUrl(String url);

}
