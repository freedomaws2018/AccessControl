package com.example.demo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.LocationDetail;
import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Service.LineBotService;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.DataBase.Service.LineUserService;
import com.example.demo.DataBase.Service.LocationService;
import com.example.demo.DataBase.Service.MemberService;
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
  private MemberService memberService;

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
        lineUser.setUserId(userId);
        lineUser.setUserName(userName);
        lineUser.setIsLeave(false);
        lineUser = lineUserService.save(lineUser);

//        Member member = new Member();
//        member.setLineUserId(lineUser.getUserId());
//        member.setBegDt(LocalDateTime.now(ZoneId.of("UTC+8")));
//        member.setEndDt(LocalDateTime.now(ZoneId.of("UTC+8")));
//        member.setIsUse(false);
//        member.setIsAdmin(false);
//        member.setFirstName(userName);
//        member.setLastName(userName);
//        member = memberService.save(member);
        logger.info("【註冊】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
      }
      return new TextMessage(userProfile.getDisplayName() + " - 註冊成功");
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /** 移除/封鎖 時 **/
  @EventMapping
  public void handleUnfollowEvent(UnfollowEvent event) {
    try {
      String userId = event.getSource().getUserId();
      LineUser lineUser = lineUserService.getByUserId(userId);

      if (lineUser != null) {
        lineUser.setIsLeave(true);
        lineUser = lineUserService.save(lineUser);

        Member member = memberService.getByUserIdAndIsUseTrue(lineUser.getUserId());
        if (member != null) {
          member.setIsUse(false);
          member.setRichMenuId(null);
          member.setLocationId(null);
          member.setLocationDetailName(null);
          member = memberService.save(member);
          if (StringUtils.isNotBlank(member.getLineUserId())) {
            lineRichMenuService.unlinkRichMenuToUser(member.getLineUserId());
          }
        }
        logger.info("【封鎖】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
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
      ex.printStackTrace();
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
  public TextMessage handleBeaconEvent(BeaconEvent event) {
    try {
      String lineUserId = event.getSource().getUserId();
      String senderId = event.getSource().getSenderId();
      String type = event.getBeacon().getType();
      String hwid = event.getBeacon().getHwid();
      String deviceMessageAsHex = event.getBeacon().getDeviceMessageAsHex();
      String textMessage = String.format("UserId: %s \nSendId: %s \nType: %s\nHWID: %s\nDeviceMessage: %s", lineUserId,
          senderId, type, hwid, deviceMessageAsHex);
      if ("enter".equals(type)) {
        // 獲取 LineUser 信息 並判斷是否存在
        Member member = memberService.getEffectiveMember(lineUserId);
        LineUser lineUser = member.getLineUser();
        Location location = member.getLocation();
        LocationDetail locationDetail = member.getLocationDetail();
        RichMenu richMenu = member.getRichMenu();

        // 為管理員
        if (member != null && lineUser != null && member.getIsAdmin()) {
          Location locAdmin = locationService.getByBeaconKey(deviceMessageAsHex);
          if (StringUtils.isNotBlank(lineUser.getUserId()) && StringUtils.isNotBlank(locAdmin.getRichMenuId())) {
            lineRichMenuService.linkRichMenuToUser(lineUser.getUserId(), locAdmin.getRichMenuId());
            member.setRichMenuLinkDateTime(LocalDateTime.now());
            memberService.save(member);
          }
        }

        // 判斷有會員 以及 有選單
        if (member != null && lineUser != null && location != null && locationDetail != null && richMenu != null) {
          if (StringUtils.isNotBlank(lineUser.getUserId()) && StringUtils.isNotBlank(richMenu.getRichMenuId())) {
            lineRichMenuService.linkRichMenuToUser(lineUser.getUserId(), richMenu.getRichMenuId());
            member.setRichMenuLinkDateTime(LocalDateTime.now());
            memberService.save(member);
          }
        }

      }
      if ("leave".equals(type)) {
        Member member = memberService.getEffectiveMember(lineUserId);
        LineUser lineUser = member.getLineUser();
        if (member != null && lineUser != null && !member.getIsAdmin()) {
          lineRichMenuService.unlinkRichMenuToUser(lineUser.getUserId());
        }
      }
      return new TextMessage(textMessage);

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

}