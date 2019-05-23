package com.example.demo.DataBase.Entity.Log;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "log_wf8266")
public class LogWf8266 {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "line_user_id", nullable = false)
  private String lineUserId;

  @Column(name = "wf8266_detail_id", nullable = false)
  private String wf8266DetailId;

  @Column(name = "location_id", nullable = false)
  private Long locationId;

}
