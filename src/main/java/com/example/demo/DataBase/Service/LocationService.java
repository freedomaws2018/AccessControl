package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.LocationDetail;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;
import com.example.demo.DataBase.Repository.LocationDetailRepository;
import com.example.demo.DataBase.Repository.LocationRepository;
import com.example.demo.DataBase.Repository.MappingEmployeeLocationRepository;

@Service
public class LocationService {

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private LocationDetailRepository locationDetailRepository;

  @Autowired
  private MappingEmployeeLocationRepository mappingEmployeeLocationRepository;

  public void updateAllIsUseFalseByLoctionId(Long locationId) {
    mappingEmployeeLocationRepository.updateAllIsUseFalseByLoctionId(locationId);
    mappingEmployeeLocationRepository.flush();
  }

  public void deleteAlLocationDetailByLocationId(Long locationId) {
    locationDetailRepository.deleteAlLocationDetailByLocationId(locationId);
    locationDetailRepository.flush();
  }

  public List<Location> getAll() {
    return this.locationRepository.findAll();
  }

  public List<Location> getAll(Sort sort) {
    return this.locationRepository.findAll(sort);
  }

  public Page<Location> getAll(Pageable pageable) {
    return this.locationRepository.findAll(pageable);
  }

  public Location getById(Long id) {
    return this.locationRepository.findById(id).orElse(null);
  }

  public List<LocationDetail> getLocationDetailByRichMenuId(String richMenuId) {
    return locationDetailRepository.findByRichMenuId(richMenuId);
  }

  public List<LocationDetail> getLocationDetailByLocationId(Long locationId) {
    return locationDetailRepository.findByLocationId(locationId);
  }

  public LocationDetail getLocationDetailByLocationIdAndLocationDetailName(Long locationId, String locationDetailName) {
    return locationDetailRepository.findByLocationIdAndName(locationId, locationDetailName).orElse(null);
  }

  public Location save(Location location) {
    return locationRepository.saveAndFlush(location);
  }

  public List<LocationDetail> saveLocationDetails(List<LocationDetail> details) {
    details = locationDetailRepository.saveAll(details);
    locationDetailRepository.flush();
    return details;
  }

  public List<MappingEmployeeLocation> saveMappingEL(List<MappingEmployeeLocation> mappings) {
    return mappingEmployeeLocationRepository.saveAll(mappings);
  }

  public void delete(Location location) {
    this.locationRepository.delete(location);
  }

  public List<Location> getByNameLike(String name) {
    return this.locationRepository.getByNameLikeOrderByLenNameAndNameLimit10("%" + name + "%");
  }
}
