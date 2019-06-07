package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingLineuserWf8266Id;
import com.example.demo.DataBase.Entity.Mapping.MappingLineuserWf8266;

public interface MappingLineuserWf8266Repository extends JpaRepository<MappingLineuserWf8266, MappingLineuserWf8266Id> {

  List<MappingLineuserWf8266> getByLineUserId(String lineUserId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE MappingLineuserWf8266 SET isUse = false WHERE lineUserId = :lineUserId")
  void updateAllIsUseFalseByLineUserId(@Param("lineUserId") String lineUserId);

  Integer countByLineUserIdAndWf8266SnAndWf8266DetailName(String lineUserId , String wf8266Sn , String wf8266DetailName);

}
