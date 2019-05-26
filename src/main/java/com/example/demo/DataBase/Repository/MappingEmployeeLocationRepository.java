package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeLocationId;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;

public interface MappingEmployeeLocationRepository  extends JpaRepository<MappingEmployeeLocation, MappingEmployeeLocationId> {

  List<MappingEmployeeLocation> findByLocationId(Long locationId);
}