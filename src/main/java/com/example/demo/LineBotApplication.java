package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Service.LineBotService;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.DataBase.Service.LineUserService;
import com.example.demo.DataBase.Service.LocationService;
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
  private LineUserService lineUserService;

  @Autowired
  private LocationService locationService;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  /** 加入/解除封鎖 時 **/
  @EventMapping
  public TextMessage handleFollowEvent(FollowEvent event) {
    try {
      String userId = event.getSource().getUserId();
      UserProfileResponse userProfile = lineBotService.getProfileByUserId(userId);
      String userName = userProfile.getDisplayName();

      LineUser lineUser = lineUserService.getByUserId(userId);
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
      lineUserService.save(lineUser);
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
    LineUser lineUser = lineUserService.getByUserIdAndIsUseTrue(userId);

    if (lineUser != null) {
      lineUser.setIsUse(false);
      lineUserService.save(lineUser);
      logger.info("【封鎖】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
    }

  }

  /** MessageEvent 不處理任何事物 **/
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
      if (replyStr != null && replyStr.length() > 0) {
        return new TextMessage(replyStr);
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
    return null;
  }

  /**
   * Beacon 觸發時
   *
   * @throws ExecutionException
   * @throws InterruptedException
   **/
  @EventMapping
  public TextMessage handleBeaconEvent(BeaconEvent event) throws InterruptedException, ExecutionException {
    String userId = event.getSource().getUserId();
    String senderId = event.getSource().getSenderId();
    String type = event.getBeacon().getType();
    String hwid = event.getBeacon().getHwid();
    String deviceMessageAsHex = event.getBeacon().getDeviceMessageAsHex();
    String textMessage = String.format("UserId: %s \nSendId: %s \nType: %s\nHWID: %s\nDeviceMessage: %s", userId,
        senderId, type, hwid, deviceMessageAsHex);
    if ("enter".equals(type)) {
      // 獲取 LineUser 信息 並判斷是否存在
      LineUser lineUser = lineUserService.getByIsUseTrueAndEffectiveAndUserId(userId);
      if (lineUser != null) {
        // 不為管理員
        if( !lineUser.getIsAdmin() ) {
          lineUserService.setRichMneuByUserId(userId);
        }

//        LocationDetail ld = locationService.getLocationDetailByLocationIdAndLocationDetailName(lineUser.getLocationId(),
//            lineUser.getLocationDetailName());
//        if (ld != null) {
//          com.example.demo.DataBase.Entity.RichMenu richMenu = lineRichMenuService.getByRichMenuId(ld.getRichMenuId());
//          if (richMenu != null) {
//            lineUserService.setRichMneuByUserId(userId);
//          }
//        }
      }
    }
    return new TextMessage(textMessage);
  }

}