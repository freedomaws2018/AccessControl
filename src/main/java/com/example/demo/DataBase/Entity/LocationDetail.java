package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_location_detail")
/**
 * 據點中的所有租契細項
 */
public class LocationDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "location_id")
  private Long locationId;

  @Column(name = "name")
  private String name;

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
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rich_menu_id", referencedColumnName = "rich_menu_id", insertable = false, updatable = false, nullable = true)
  private RichMenu richMenu;

}
