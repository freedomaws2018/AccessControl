package com.example.demo.DataBase.Entity;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.example.demo.DataBase.Entity.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_wf8266")
public class Wf8266 extends BaseEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private String triggerText;

	/** 區域編號 - 對應 Location **/
	@Column(name = "location_id")
	private Long locationId;

	/** 執行動作名稱 **/
	@Column(name = "name")
	private String name;

	/** 是否啟用 **/
	@Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isUse = new Boolean(false);

	/** wf8266 編號 **/
	@Column(name = "sn", updatable = false)
	private String sn;

	/** wf8266 線上金鑰 **/
	@Column(name = "key", updatable = false)
	private String key;

	@Column(name = "cmd")
	private String cmd;

	/**
	 * 觸發腳位 <BR>
	 * 1,2,3,4,5,12,13,14,15,16
	 **/
	@Column(name = "data1")
	private String data1;

	/**
	 * 觸發行為 <BR>
	 * GPIO:[0:開啟] [1:關閉] [2:反向 Toggle] [3:按鍵 0->1->0] [4:按鍵 1->0->1]
	 **/
	@Column(name = "data2")
	private String data2;

	/** 出發前 回應 **/
	@Column(name = "reply1")
	private String reply1;

	/** 出發後 回應 **/
	@Column(name = "reply2")
	private String reply2;

	public String getStatusUrl() {
		// https://service.wf8266.com/api/mqtt/SN/RequestState/key
		return String.format("https://service.wf8266.com/api/mqtt/%s/RequestState/%s", this.getSn(), this.getKey());
	}

	public String getTriggerUrl() {
		// https://service.wf8266.com/api/mqtt/SN/CMD/KEY/DATA1[,DATA2]
		return String.format("https://service.wf8266.com/api/mqtt/%s/%s/%s/%s", this.getSn(), this.getCmd(),
				this.getKey(), String.join(",", Arrays.asList(this.data1, this.data2).stream()
						.filter(StringUtils::isNotBlank).collect(Collectors.toList())));
	}
}
