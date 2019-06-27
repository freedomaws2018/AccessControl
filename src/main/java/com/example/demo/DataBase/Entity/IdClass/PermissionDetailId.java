package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class PermissionDetailId implements Serializable {

  private static final long serialVersionUID = -2552677826675926528L;

  private String permissionKey;

  private String type;

}
