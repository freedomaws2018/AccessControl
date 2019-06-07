package com.example.demo.DataBase.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.example.demo.DataBase.Entity.IdClass.LocationDetailId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_location_detail")
@IdClass(LocationDetailId.class)
/**
 * 據點中的所有租契細項
 */
public class LocationDetail implements Serializable {

  private static final long serialVersionUID = 7537909914347377150L;

  @Id
  @Column(name = "name")
  private String name;

  @Id
  @Column(name = "location_id")
  private Long locationId;

  /** 是否為實體空間 **/
  @Column(name = "is_space")
  private Boolean isSpace;

  /** 租金/月 **/
  @Column(name = "rent_month")
  private Integer rentMonth;

  /** 稅金/月 **/
  @Column(name = "tax_month")
  private Integer taxMonth;

  /** 電費/度 **/
  @Column(name = "electricity_fee_degree")
  private Double electricityFeeDegree;

  /** LINE@選單 **/
  @Column(name = "rich_menu_id")
  private String richMenuId;
  @Transient
  private RichMenu richMenu;

}
