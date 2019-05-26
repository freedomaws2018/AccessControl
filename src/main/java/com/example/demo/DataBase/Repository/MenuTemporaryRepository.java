package com.example.demo.DataBase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.MenuTemporary;

@Repository
public interface MenuTemporaryRepository extends JpaRepository<MenuTemporary, Long> {

  Optional<MenuTemporary> getByEmployeeId(Long employeeId);

}
