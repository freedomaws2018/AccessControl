package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_wf8266")
public class Wf8266 extends BaseEntity {

	/** wf8266 編號 **/
	@Id
	@Column(name = "sn", updatable = false)
	private String sn;

	@Column(name = "key", updatable = false)
	private String key;

	/** 區域編號 - 對應 Location **/
	@Column(name = "location_id")
	private Long locationId;

	/** 是否啟用 **/
	@Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isUse = new Boolean(false);

	/** 是否啟用於 MessageEvent **/
	@Column(name = "is_message_event", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isMessageEvent = new Boolean(true);

	/** 是否啟用於 PostbackEvent **/
	@Column(name = "is_postback_event", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isPostbackEvent = new Boolean(true);

	public String getStatusUrl() {
		// https://service.wf8266.com/api/mqtt/SN/RequestState/key
		return String.format("https://service.wf8266.com/api/mqtt/%s/RequestState/%s", this.getSn(), this.getKey());
	}
}
