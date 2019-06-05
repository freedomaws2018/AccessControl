package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.DataBase.Repository.RichMenuTemplateRepository;

@Service
public class RichMenuService {

  @Autowired
  private RichMenuRepository richMenuRepository;

  @Autowired
  private RichMenuTemplateRepository richMenuTemplateRepository;

  public List<RichMenu> getAllRichMenu() {
    return richMenuRepository.findAll();
  }

}
