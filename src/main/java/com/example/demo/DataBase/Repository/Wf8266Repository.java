package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.Wf8266;

public interface Wf8266Repository extends JpaRepository<Wf8266, String> {

	List<Wf8266> getBySn(String sn);

	List<Wf8266> getByTriggerTextIn(List<String> triggerTexts);

}
