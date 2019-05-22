package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseMapping;
import com.example.demo.DataBase.Entity.IdClass.MappingRichMenuAndLocationId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_richmenu_location")
@IdClass(MappingRichMenuAndLocationId.class)
public class MappingRichMenuAndLocation extends BaseMapping {

  @Id
  @Column(name = "rich_menu_id")
  private String richMenuId;

  @Id
  @Column(name = "location_id")
  private Long locationId;

}
