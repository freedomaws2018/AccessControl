package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingWf8266LineuserId;
import com.example.demo.DataBase.Entity.Mapping.MappingWf8266Lineuser;

public interface MappingWf8266LineuserRepository
		extends JpaRepository<MappingWf8266Lineuser, MappingWf8266LineuserId> {

	List<MappingWf8266Lineuser> getByLineUserId(String lineUserId);


	@Modifying
	@Transactional
	@Query(value = "UPDATE MappingWf8266Lineuser SET isUse = false WHERE lineUserId = :lineUserId")
	void updateAllIsUseFalseByLineUserId(@Param("lineUserId") String lineUserId);

}
