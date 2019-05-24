package com.example.demo.DataBase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Position;
import com.example.demo.DataBase.Repository.PositionRepository;

@Service
public class PositionService {

  @Autowired
  private PositionRepository positionRepository;

  public Page<Position> getAll(Pageable pageable){
    return positionRepository.findAll(pageable);
  }
}
