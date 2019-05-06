package com.example.demo.DataBase.Entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingWf8266DetailAndUserId implements Serializable {

	private static final long serialVersionUID = -4781425387685569479L;

	private String userId;

	private String triggerText;

}