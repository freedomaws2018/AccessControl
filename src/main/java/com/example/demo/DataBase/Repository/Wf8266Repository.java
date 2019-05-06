package com.example.demo.DataBase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DataBase.Entity.Wf8266;

public interface Wf8266Repository extends JpaRepository<Wf8266, String> {

	Optional<Wf8266> getBySn(String sn);

}
