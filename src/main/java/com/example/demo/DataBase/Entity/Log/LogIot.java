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
@Table(name = "log_iot")
public class LogIot {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "member_id", nullable = false)
  private Long memberId;


  @Column(name = "location_id", nullable = false)
  private Long locationId;

  @Column(name = "wf8266_sn")
  private String wf8266Sn;

  @Column(name = "wf8266_detail_name", nullable = false)
  private String wf8266DetailName;

  public LogIot() {}

  public LogIot(Long memberId , Long locationId , String wf8266Sn , String wf8266DetailName) {
    this.memberId = memberId;
    this.locationId = locationId;
    this.wf8266Sn = wf8266Sn;
    this.wf8266DetailName = wf8266DetailName;
  }

}
