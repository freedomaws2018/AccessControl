package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Repository.LineUserRepository;

@Service
public class LineUserService {

  @Autowired
  private LineUserRepository lineUserRepository;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  public List<LineUser> getAllLineUserByEffectiveAndUserId() {
    return lineUserRepository.getByIsUseTrueAndEffective();
  }

  public LineUser getByIsUseTrueAndEffectiveAndUserId(String userId) {
    return lineUserRepository.getByIsUseTrueAndEffectiveAndUserId(userId).orElse(null);
  }

  public LineUser getByUserIdAndIsUseTrue(String userId) {
    return lineUserRepository.getByUserIdAndIsUseTrue(userId).orElse(null);
  }

  public LineUser getByUserId(String userId) {
    return lineUserRepository.getByUserId(userId).orElse(null);
  }

  public LineUser setRichMneuByUserId(String userId) {
    LineUser lineuser = lineUserRepository.findById(userId).orElse(null);
    if (lineuser != null) {
      String lineUserId = lineuser.getUserId();
      String richMenuId = lineuser.getRichMenuId();
      if (StringUtils.isNotBlank(richMenuId)) {
        try {
          lineRichMenuService.linkRichMenuToUser(lineUserId, richMenuId);
          lineUserRepository.updateRichMenuLinkDatetime(lineUserId);
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }
    }
    return lineuser;
  }

  public LineUser save(LineUser lineUser) {
    return lineUserRepository.saveAndFlush(lineUser);
  }

}
