package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingRichmenuLocationdetailId;

import lombok.Data;

@Data
@Entity
@Table(name = "mapping_richmenu_locationdetail")
@IdClass(MappingRichmenuLocationdetailId.class)
public class MappingRichmenuLocationdetail {

  @Id
  @Column(name = "rich_menu_id")
  private String richMenuId;

  @Id
  @Column(name = "location_id")
  private Long locationDetailId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
