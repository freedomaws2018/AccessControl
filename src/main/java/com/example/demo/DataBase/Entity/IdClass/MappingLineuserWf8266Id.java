package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingLineuserWf8266Id implements Serializable {

	private static final long serialVersionUID = -4781425387685569479L;

	private String wf8266DetailName;

	private String wf8266Sn;

	private String lineUserId;

}