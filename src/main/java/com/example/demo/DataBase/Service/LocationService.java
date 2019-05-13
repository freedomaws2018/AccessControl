package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	public List<Location> getAll() {
		return this.locationRepository.findAll();
	}

	public Page<Location> getAll(Pageable pageable) {
		return this.locationRepository.findAll(pageable);
	}

	public Location getById(Long id) {
		return this.locationRepository.findById(id).orElse(null);
	}

	public Location save(Location location) {
		return this.locationRepository.save(location);
	}

	public void delete(Location location) {
		this.locationRepository.delete(location);
	}
}
