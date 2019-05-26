package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeMenu;

public interface MappingEmployeeMenuRepository extends JpaRepository<MappingEmployeeMenu, MappingEmployeeLocation> {

  List<MappingEmployeeMenu> findByEmployeeIdAndIsUseTrue(Long employeeId);
}
