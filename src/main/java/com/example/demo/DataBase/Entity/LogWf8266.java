package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "log_wf8266")
public class LogWf8266 extends BaseLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seqno;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "wf8266_trigger", nullable = false)
	private String wf8266trigger;

	public LogWf8266() {

	}

	public LogWf8266(String userId, String wf8266trigger) {
		super();
		this.userId = userId;
		this.wf8266trigger = wf8266trigger;
	}

}
