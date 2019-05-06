package com.example.demo.DataBase.Entity;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_wf8266_detail")
public class Wf8266Detail {

	/** 呼叫參數 , 呼叫時前面必須加 # **/
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "trigger_text")
	private String triggerText;

	@Column(name = "sn")
	private String sn;

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

	/** 執行動作名稱 **/
	@Column(name = "name")
	private String name;

	/** 出發後 回應 **/
	@Column(name = "reply")
	private String reply;

	/** 是否啟用 **/
	@Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isUse = new Boolean(false);

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "sn", insertable = false, updatable = false)
	private Wf8266 wf8266;

	public String getStatusUrl() {
		// https://service.wf8266.com/api/mqtt/SN/RequestState/key
		return String.format("https://service.wf8266.com/api/mqtt/%s/RequestState/%s", wf8266.getSn(), wf8266.getKey());
	}

	public String getTriggerUrl() {
		// https://service.wf8266.com/api/mqtt/SN/CMD/KEY/DATA1[,DATA2]
		return String.format("https://service.wf8266.com/api/mqtt/%s/%s/%s/%s", wf8266.getSn(), getCmd(), wf8266.getKey(),
		    String.join(",",
		        Arrays.asList(data1, data2).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())));
	}

}
