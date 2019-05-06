package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.Wf8266Detail;

public interface Wf8266DetailRepository extends JpaRepository<Wf8266Detail, String> {

	List<Wf8266Detail> getBySn(String sn, Sort sort);

	Optional<Wf8266Detail> getByTriggerTextAndIsUseTrue(String triggerText);

	List<Wf8266Detail> getByTriggerTextInAndIsUseTrue(List<String> triggerTexts);


//	@Query("")
//	Integer queryByUserIdAndTriggerTextPermission(String userId , String triggerText); // 0 : 無權限 , 1 : 有權限

}
