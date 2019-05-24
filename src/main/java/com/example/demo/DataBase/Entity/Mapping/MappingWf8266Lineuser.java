package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingWf8266LineuserId;

import lombok.Data;

@Data
@Entity
@Table(name = "mapping_wf8266_lineuser")
@IdClass(MappingWf8266LineuserId.class)
public class MappingWf8266Lineuser {

  @Id
  @Column(name = "wf8266_id")
  private String wf8266Id;

  @Id
  @Column(name = "line_user_id")
  private String lineUserId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
