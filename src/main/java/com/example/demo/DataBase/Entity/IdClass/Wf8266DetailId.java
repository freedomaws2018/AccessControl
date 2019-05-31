package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Wf8266DetailId implements Serializable {

  private static final long serialVersionUID = 6625432752238398853L;

  private String name;

  private String wf8266Sn;
}
