package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class LocationDetailId implements Serializable {

  private static final long serialVersionUID = 7703936770660621674L;

  private String name;

  private Long locationId;
}
