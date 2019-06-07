package com.example.demo.DataBase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Repository.LineUserRepository;

@Service
public class LineUserService {

  @Autowired
  private LineUserRepository lineUserRepository;

  public LineUser getByIsUseTrueAndEffectiveAndUserId(String userId) {
    return lineUserRepository.getByIsUseTrueAndEffectiveAndUserId(userId).orElse(null);
  }

  public LineUser getByUserIdAndIsUseTrue(String userId) {
    return lineUserRepository.getByUserIdAndIsUseTrue(userId).orElse(null);
  }

  public LineUser getByUserId(String userId) {
    return lineUserRepository.getByUserId(userId).orElse(null);
  }

  public LineUser setRichMenuIdByUserId(String userId, String richMenuId) {
    LineUser lineuser = lineUserRepository.findById(userId).orElse(null);
    if (lineuser != null) {
      lineuser.setRichMenuId(richMenuId);
      lineUserRepository.saveAndFlush(lineuser);
    }
    return lineuser;
  }

  public LineUser save(LineUser lineUser) {
    return lineUserRepository.saveAndFlush(lineUser);
  }

}
