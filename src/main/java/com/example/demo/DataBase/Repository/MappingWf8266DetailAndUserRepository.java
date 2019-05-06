package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.MappingWf8266DetailAndUser;
import com.example.demo.DataBase.Entity.MappingWf8266DetailAndUserId;

public interface MappingWf8266DetailAndUserRepository extends JpaRepository<MappingWf8266DetailAndUser, MappingWf8266DetailAndUserId> {

	List<MappingWf8266DetailAndUser> getByUserId(String userId);

	MappingWf8266DetailAndUser getByIsUseTrueAndUserIdAndTriggerText(String userId, String triggerText);

	List<MappingWf8266DetailAndUser> getByIsUseTrueAndUserIdAndTriggerTextIn(String userId, List<String> triggerTexts);

	@Modifying
	@Transactional
	@Query(value = "UPDATE MappingWf8266DetailAndUser SET isUse = false WHERE userId = :userId")
	public void updateAllIsUseFalseByUserId(@Param("userId") String userId);

}
