package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.demo.DataBase.Entity.Location;

import lombok.Data;

@Data
public class FormLocation {

	private Long id;

	private String name;

	private String address;

	private String phone;

	private List<Long> keepers = new ArrayList<>();

	public Location toEntity() {
		Location location = new Location();
		BeanUtils.copyProperties(this, location);
		return location;
	}
}
