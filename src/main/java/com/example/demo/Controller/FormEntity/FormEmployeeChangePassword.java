package com.example.demo.Controller.FormEntity;

import lombok.Data;

@Data
public class FormEmployeeChangePassword {

  private String oriPassword;

  private String newPassword1;

  private String newPassword2;
}
