package com.example.demo.DataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
