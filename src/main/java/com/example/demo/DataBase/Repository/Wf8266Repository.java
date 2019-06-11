package com.example.demo.DataBase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.Wf8266;

public interface Wf8266Repository extends JpaRepository<Wf8266, String> {

  @Transactional
	@Query(value = "SELECT * FROM tbl_wf8266 WHERE sn = :sn ORDER BY location_id ASC ", nativeQuery = true)
	Optional<Wf8266> getBySnOrderByLocationIdAsc(@Param("sn") String sn);

}
