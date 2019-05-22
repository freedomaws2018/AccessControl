package com.example.demo.DataBase.Entity.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseLogEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "log_wf8266")
public class LogWf8266 extends BaseLogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "line_user_id", nullable = false)
  private String lineUserId;

  @Column(name = "wf8266_detail_id", nullable = false)
  private String wf8266DetailId;

  @Column(name = "location_id", nullable = false)
  private Long locationId;

}
