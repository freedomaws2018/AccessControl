package com.example.demo.Controller.FormEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;

import lombok.Data;

@Data
public class FormWf8266 {

	private String sn;

	private String key;

	private Boolean isUse;

	private Boolean isMessageEvent;

	private Boolean isPostbackEvent;

	private List<FormWf8266Detail> details;

	public Wf8266 toWf8266() {
		Wf8266 wf8266 = new Wf8266();
		wf8266.setSn(sn);
		wf8266.setKey(key);
		wf8266.setIsUse(isUse);
		wf8266.setIsMessageEvent(isMessageEvent);
		wf8266.setIsPostbackEvent(isPostbackEvent);
		return wf8266;
	}

	public List<Wf8266Detail> toWf8266DetailsSaveOrUpdate() {
		if (details != null && !details.isEmpty())
			return details.stream().map(FormWf8266Detail::toWf8266DetailSaveOrUpdate).filter(Objects::nonNull).map(detail -> {
				detail.setSn(sn);
				return detail;
			}).collect(Collectors.toList());
		else
			return null;
	}

	public List<String> toWf8266DetailsDelete() {
		if (details != null && !details.isEmpty())
			return details.stream().map(FormWf8266Detail::toWf8266DetailDelete).filter(Objects::nonNull).distinct()
			    .collect(Collectors.toList());
		else
			return null;
	}

}
