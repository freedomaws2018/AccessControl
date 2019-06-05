package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Service.LineBotService;
import com.linecorp.bot.model.event.BeaconEvent;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
//@PropertySource("classpath:config/linebot.yml")
public class LineBotApplication {

  private static Logger logger = LoggerFactory.getLogger(LineBotApplication.class);

  @Autowired
  private LineBotService lineBotService;

  @Autowired
  private LineUserRepository lineUserRepository;

  /** 加入/解除封鎖 時 **/
  @EventMapping
  public TextMessage handleFollowEvent(FollowEvent event) {
    try {
      String userId = event.getSource().getUserId();
      UserProfileResponse userProfile = lineBotService.getProfileByUserId(userId);
      String userName = userProfile.getDisplayName();

      LineUser lineUser = lineUserRepository.getByUserId(userId).orElse(null);
      if (lineUser == null) {
        lineUser = new LineUser();
        lineUser.setCreateDate(LocalDateTime.now(ZoneId.of("UTC+8")));
        lineUser.setUserName(userName);
        lineUser.setUserId(userId);
        lineUser.setBegDt(LocalDateTime.now());
        lineUser.setEndDt(LocalDateTime.now());
        lineUser.setIsUse(false);
      } else {
        lineUser.setIsUse(false);
      }
      lineUserRepository.save(lineUser);
      logger.info("【註冊】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
      return new TextMessage(userProfile.getDisplayName() + " - 註冊成功");
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /** 移除/封鎖 時 **/
  @EventMapping
  public void handleUnfollowEvent(UnfollowEvent event) {
    String userId = event.getSource().getUserId();
    LineUser lineUser = this.lineUserRepository.getByUserIdAndIsUseTrue(userId).orElse(null);

    if (lineUser != null) {
      lineUser.setIsUse(false);
      this.lineUserRepository.save(lineUser);
      logger.info("【封鎖】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
    }

  }

  /** MessageEvent 不處理任何事物  **/
  @EventMapping
  public void handleMessageEvent(MessageEvent<MessageContent> event) {
  }

  /** PostBack 時 **/
  @EventMapping
  public TextMessage handlePostbackEvent(PostbackEvent event) {
    try {
      String userId = event.getSource().getUserId();
      String text = event.getPostbackContent().getData();
      List<String> triggerTexts = Arrays.asList(text.split(","));
      String replyStr = lineBotService.wf8266Handle(userId, triggerTexts);
      if (replyStr != null) {
        return new TextMessage(replyStr);
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
    return null;
  }

  /** Beacon 觸發時 **/
  @EventMapping
  public TextMessage handleBeaconEvent(BeaconEvent event) {
    String text = event.getBeacon().getType();
    return new TextMessage(text);
  }

}