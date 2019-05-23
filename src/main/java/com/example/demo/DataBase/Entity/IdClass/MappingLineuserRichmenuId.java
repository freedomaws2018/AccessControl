package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingLineuserRichmenuId implements Serializable {

  private static final long serialVersionUID = 4586775162002923139L;

  private String lineUserId;

  private String richMenuId;
}
