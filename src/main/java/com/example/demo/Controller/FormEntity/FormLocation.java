package com.example.demo.Controller.FormEntity;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.Wf8266;

import lombok.Data;

@Data
public class FormLocation {

	private Long id;

	private String name;

	private String address;

	private String phone;

	private String keeper;

	private List<Wf8266> wf8266s;

	public Location toEntity() {
		Location location = new Location();
		BeanUtils.copyProperties(this, location);
		return location;
	}
}
