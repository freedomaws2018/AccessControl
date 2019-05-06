package com.example.demo.Controller.FormEntity;

import com.example.demo.DataBase.Entity.Wf8266Detail;

import lombok.Data;

@Data
public class FormWf8266Detail {

	private String status;

	private String triggerText;

	private String sn;

	private String cmd;

	private String data1;

	private String data2;

	private String name;

	private String reply;

	private Boolean isUse;

	public Wf8266Detail toWf8266DetailSaveOrUpdate() {
		if ("N".equals(status) || "U".equals(status)) {
			Wf8266Detail wd = new Wf8266Detail();
			wd.setTriggerText(triggerText);
			wd.setSn(sn);
			wd.setCmd(cmd);
			wd.setData1(data1);
			wd.setData2(data2);
			wd.setName(name);
			wd.setReply(reply);
			wd.setIsUse(isUse);
			return wd;
		}
		return null;
	}

	public String toWf8266DetailDelete() {
		if ("D".equals(status)) {
			return triggerText;
		}
		return null;
	}

}
