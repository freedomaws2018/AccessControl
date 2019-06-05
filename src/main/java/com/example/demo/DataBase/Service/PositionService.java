package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Position;
import com.example.demo.DataBase.Entity.Mapping.MappingPositionPermissionPermissiondetail;
import com.example.demo.DataBase.Repository.MappingPositionPermissionPermissiondetailRepository;
import com.example.demo.DataBase.Repository.PositionRepository;

@Service
public class PositionService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private MappingPositionPermissionPermissiondetailRepository mappingPositionPermissionPermissiondetailRepository;

  public List<Position> getAll() {
    return positionRepository.findAll(Sort.by(Order.asc("id")));
  }

  public Page<Position> getAll(Pageable pageable) {
    return positionRepository.findAll(pageable);
  }

  public Position getById(Long id) {
    return positionRepository.findById(id).orElse(null);
  }

  public Boolean existByName(String name) {
    return positionRepository.countByName(name) > 0 ? true : false;
  }

  public Position save(Position position) {
    return positionRepository.saveAndFlush(position);
  }

  public void deleteById(Long positionId) {
    positionRepository.deleteById(positionId);
  }

  public List<String> findMappingPPPByPositionIdAndIsUseTrue(Long positionId) {
    List<MappingPositionPermissionPermissiondetail> mappingPPPs = mappingPositionPermissionPermissiondetailRepository
        .findByPositionIdAndIsUseTrue(positionId);
    return mappingPPPs.stream().map(m -> m.getPermissionId() + m.getPermissionDetailType())
        .collect(Collectors.toList());
  }

  public void updateAllIsUseFalseWithPositionId(Long positionId) {
    mappingPositionPermissionPermissiondetailRepository.updateAllIsUseFalseWithPositionId(positionId);
  }

  public List<MappingPositionPermissionPermissiondetail> saveAllMappingPPP(Long positionId,
      List<MappingPositionPermissionPermissiondetail> list) {
    this.updateAllIsUseFalseWithPositionId(positionId);
    return mappingPositionPermissionPermissiondetailRepository.saveAll(list);
  }

}
