package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.DataBase.Entity.Log.LogIot;

public interface LogIotRepository extends JpaRepository<LogIot, String> {

  @Query(value = "SELECT * FROM public.log_iot WHERE create_date between (now() - interval '3 month')\\:\\:date and now() ; ", nativeQuery = true)
  List<LogIot> findAllInThreeMonth();

}
