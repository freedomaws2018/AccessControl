package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.LocationDetail;
import com.example.demo.DataBase.Entity.IdClass.LocationDetailId;

@Repository
public interface LocationDetailRepository extends JpaRepository<LocationDetail, LocationDetailId> {

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query(value = "DELETE FROM tbl_location_detail WHERE location_id = :locationId ; ", nativeQuery = true)
  void deleteAlLocationDetailByLocationId(@Param("locationId") Long locationId);

  List<LocationDetail> findByLocationId(Long locationId);

  List<LocationDetail> findByRichMenuId(String richMenuId);

  Optional<LocationDetail> findByLocationIdAndName(Long locationId , String name);

}
