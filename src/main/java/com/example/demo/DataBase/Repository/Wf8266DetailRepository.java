package com.example.demo.DataBase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Entity.IdClass.Wf8266DetailId;

public interface Wf8266DetailRepository extends JpaRepository<Wf8266Detail, Wf8266DetailId> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM tbl_wf8266_detail WHERE wf8266_sn = :wf8266Sn ; ", nativeQuery = true)
  void deleteDetailByWf8266Sn(@Param("wf8266Sn") String wf8266Sn);

  @Transactional
  Optional<Wf8266Detail> findByWf8266SnAndName(String sn , String name);

}
