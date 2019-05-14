package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;

import lombok.Data;

@Data
public class FormWf8266 {

	private String sn;

	private String key;

	private Boolean isUse;

	private Long locationId;

	private List<FormWf8266Detail> detail;

	public List<Wf8266Detail> toDeleteEntity() {
		List<Wf8266Detail> details = new ArrayList<>();
		if (this.detail != null) {
			List<FormWf8266Detail> deleteList = this.detail.stream().filter(detail -> "D".equals(detail.getStatus()))
					.collect(Collectors.toList());
			deleteList.forEach(dl -> {
				Wf8266Detail temp = new Wf8266Detail();
				BeanUtils.copyProperties(dl, temp);
				details.add(temp);
			});
			return details;
		} else {
			return null;
		}
	}

	public Wf8266 toEntity() {
		Wf8266 wf8266 = new Wf8266();
		List<Wf8266Detail> list = wf8266.getDetails();

		BeanUtils.copyProperties(this, wf8266);
		if (this.detail != null) {
			List<FormWf8266Detail> updateList = this.detail.stream()
					.filter(detail -> Arrays.asList("N", "U").contains(detail.getStatus()))
					.collect(Collectors.toList());
			updateList.forEach(dl -> {
				Wf8266Detail temp = new Wf8266Detail();
				BeanUtils.copyProperties(dl, temp);
				temp.setWf8266(wf8266);
				list.add(temp);
			});
		}

		return wf8266;
	}

}