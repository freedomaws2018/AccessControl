package com.example.demo.Controller.FormEntity;

import lombok.Data;

@Data
public class FormLocaionDetail {

  /** 地點別名 **/
  private String name;

  private Boolean isSpace;

  /** 租金/月 **/
  private Integer rentMonth;

  /** 稅金/月 **/
  private Integer taxMonth;

  /** 電費/度 **/
  private Double electricityFeeDegree;

  /** 對應Line@選單 **/
  private String richMenuId;

}
