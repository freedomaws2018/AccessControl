package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.RichMenu;

public interface RichMenuRepository extends JpaRepository<RichMenu, String> {

//	@Transactional
//	@Query(value = "SELECT rm FROM RichMenu rm WHERE rm.name = :name ")
//	Optional<RichMenu> getByName(@Param("name") String name);
//
  @Transactional
  @Query(value = "SELECT rm FROM RichMenu rm WHERE rm.name LIKE CONCAT('%',:name,'%')")
  List<RichMenu> getByNameLike(@Param("name") String name);

}
