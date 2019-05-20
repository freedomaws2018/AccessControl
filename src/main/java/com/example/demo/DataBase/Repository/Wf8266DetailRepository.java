package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.Wf8266Detail;

public interface Wf8266DetailRepository extends JpaRepository<Wf8266Detail, String> {

  List<Wf8266Detail> getByTriggerTextIn(List<String> triggerText);

}
