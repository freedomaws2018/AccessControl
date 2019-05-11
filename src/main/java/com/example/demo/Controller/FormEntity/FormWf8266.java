package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.example.demo.DataBase.Entity.Wf8266;

import lombok.Data;

@Data
public class FormWf8266 {

	private String sn;

	private String key;

	private List<FormWf8266Detail> detail;

	public List<Wf8266> toDeleteEntity() {
		List<Wf8266> list = new ArrayList<>();

		List<FormWf8266Detail> deleteList = this.detail.stream().filter(detail -> "D".equals(detail.getStatus()))
				.collect(Collectors.toList());
		deleteList.forEach(dl -> {
			Wf8266 temp = new Wf8266();
			BeanUtils.copyProperties(dl, temp);
			temp.setSn(this.getSn());
			temp.setKey(this.getKey());
			list.add(temp);
		});

		return list;
	}

	public List<Wf8266> toEntity() {
		List<Wf8266> list = new ArrayList<>();

		List<FormWf8266Detail> updateList = this.detail.stream()
				.filter(detail -> Arrays.asList("N", "U").contains(detail.getStatus())).collect(Collectors.toList());
		updateList.forEach(dl -> {
			Wf8266 temp = new Wf8266();
			BeanUtils.copyProperties(dl, temp);
			temp.setSn(this.getSn());
			temp.setKey(this.getKey());
			list.add(temp);
		});

		return list;
	}

}