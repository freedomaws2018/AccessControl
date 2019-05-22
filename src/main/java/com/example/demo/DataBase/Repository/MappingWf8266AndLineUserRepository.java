package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingWf8266AndLineUserId;
import com.example.demo.DataBase.Entity.Mapping.MappingWf8266AndLineUser;

public interface MappingWf8266AndLineUserRepository
		extends JpaRepository<MappingWf8266AndLineUser, MappingWf8266AndLineUserId> {

	List<MappingWf8266AndLineUser> getByLineUserId(String lineUserId);
	

	@Modifying
	@Transactional
	@Query(value = "UPDATE MappingWf8266AndLineUser SET isUse = false WHERE lineUserId = :lineUserId")
	void updateAllIsUseFalseByLineUserId(@Param("lineUserId") String lineUserId);

}
