package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseMapping;
import com.example.demo.DataBase.Entity.IdClass.MappingWf8266AndLineUserId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_wf8266_lineuser")
@IdClass(MappingWf8266AndLineUserId.class)
public class MappingWf8266AndLineUser extends BaseMapping {

  @Id
  @Column(name = "wf8266_id")
  private String wf8266Id;

  @Id
  @Column(name = "line_user_id")
  private String lineUserId;

}
