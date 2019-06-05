package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeLocationId;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;

public interface MappingEmployeeLocationRepository
    extends JpaRepository<MappingEmployeeLocation, MappingEmployeeLocationId> {

  List<MappingEmployeeLocation> findByLocationId(Long locationId);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query(value = "UPDATE mapping_employee_location SET is_use = false WHERE location_id = :locationId", nativeQuery = true)
  void updateAllIsUseFalseByLoctionId(@Param("locationId") Long locationId);

}
