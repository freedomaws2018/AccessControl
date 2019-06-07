package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class XMappingRichmenuLocationdetailId implements Serializable {

  private static final long serialVersionUID = -7005689994095050733L;

  private String richMenuId;

  private Long locationDetailId;
}
