package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Repository.LineUserRepository;

@Service
public class LineUserService {

  @Autowired
  private LineUserRepository lineUserRepository;

  public List<LineUser> getAll() {
    return lineUserRepository.findAll();
  }

  public Page<LineUser> getAll(Pageable pageable) {
    return lineUserRepository.findAll(pageable);
  }

  public List<LineUser> getByFilterAll(String term){
    return lineUserRepository.getByFilterAll(term);
  }

  public LineUser getByUserId(String userId) {
    return lineUserRepository.getByUserId(userId).orElse(null);
  }

  public LineUser save(LineUser lineUser) {
    return lineUserRepository.saveAndFlush(lineUser);
  }

}
