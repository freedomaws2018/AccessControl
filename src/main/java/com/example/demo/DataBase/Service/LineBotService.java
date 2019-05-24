package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;

@Service
public class LineBotService {

  private static Logger logger = LoggerFactory.getLogger(LineBotService.class);

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Value("${line.bot.channelToken}")
  private String channelAccessToken;

  @Value("${line.GetProfileUrl}")
  private String getProfileUrl;

  /** 取得用戶資料 By UserId **/
  public UserProfileResponse getProfileByUserId(String userId) {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    try {
      return client.getProfile(userId).get();
    } catch (InterruptedException | ExecutionException e1) {
      return null;
    }

  }

  /**
   * 回應訊息 <BR>
   * doReplyMessage(String replyToken , String text);
   **/
  public BotApiResponse doReplyMessage(String replyToken, String text) {
    try {
      ReplyMessage reply = new ReplyMessage(replyToken, new TextMessage(text));
      LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
      return client.replyMessage(reply).get();
    } catch (Exception e) {
      // TODO : 紀錄LOG
      logger.warn(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 回應訊息 <BR>
   * ReplyMessage replyMessage = new ReplyMessage(replyToken, new
   * TextMessage(text)); <BR>
   * doReplyMessage(replyMessage);
   **/
  public BotApiResponse doReplyMessage(ReplyMessage replyMessage) {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    try {
      return client.replyMessage(replyMessage).get();
    } catch (Exception e) {
      // TODO : 紀錄LOG
      logger.warn(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 發送訊息 <BR>
   * PushMessage pushMessage = new PushMessage(event.getSource().getUserId(), new
   * TextMessage("Push"));<BR>
   * doPushMessage(pushMessage);
   **/
  public BotApiResponse doPushMessage(PushMessage pushMessage) {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    try {
      return client.pushMessage(pushMessage).get();
    } catch (Exception e) {
      return null; // TODO : 紀錄LOG
    }
  }

  /** Wf8266 所有指令處理區 **/
  public String wf8266Handle(String userId, List<String> triggerTexts) {
    System.err.println("triggerTexts1" + triggerTexts);
    // 指令開頭過濾，指令開頭均為 #
    List<String> triggerTexts1 = triggerTexts.stream().filter(triggerText -> triggerText.matches("^#.*"))
        .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

    if (triggerTexts1 == null || triggerTexts1.isEmpty()) {
      return null;
    }

    // 觸動指令
    List<String> triggerTexts2 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^#.*"))
        .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

    // 換頁指令
    List<String> triggerTexts3 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^>.*"))
        .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());
    System.err.println("triggerTexts2" + triggerTexts2);
    System.err.println("triggerTexts3" + triggerTexts3);
    return null;

//  LineUser lineUser = null;
//  if (!triggerTexts2.isEmpty() || !triggerTexts3.isEmpty()) {
//    // 1. 有指令需要執行前 先確認使用者權限
//    lineUser = this.lineUserRepository.getEffectiveUser(userId).orElse(null);
//    if (lineUser == null) {
//      this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage("使用者沒有權限")));
//      return;
//    }
//  }

//  if (!triggerTexts2.isEmpty()) {
//    List<Wf8266Detail> wDetails = this.wf8266DetailRepository.getByTriggerTextInAndIsUseTrue(triggerTexts2);
//
//    if (wDetails != null && !wDetails.isEmpty()) {
//      //
//      List<MappingWf8266DetailAndUser> mappingWdUs = this.mappingWf8266DetailAndUserRepository
//          .getByIsUseTrueAndUserIdAndTriggerTextIn(userId,
//              wDetails.stream().map(Wf8266Detail::getTriggerText).collect(Collectors.toList()));
//
//      if (wDetails.size() == mappingWdUs.size()) { // 所以指令均有權限
//        wDetails.stream().map(Wf8266Detail::getTriggerUrl).forEach(HttpUtils::doGet);
//        List<String> replys = wDetails.stream().map(Wf8266Detail::getReply).collect(Collectors.toList());
////        System.err.println(lineUser.getUserName() + ":" + String.join("\r\n", replys));
//
//        new Thread(() -> {
//          triggerTexts.forEach(triggerText -> {
//            this.loggerWf8266Repository.save(new LogWf8266(userId, triggerText));
//          });
//        }).start();
//
//        logger.info("【指令】 " + lineUser.getUserName() + ":" + String.join("\r\n", replys));
//        this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage(String.join("\r\n", replys))));
//      } else {
//        this.doReplyMessage(new ReplyMessage(replyToken,
//            new TextMessage(mappingWdUs.isEmpty() ? "權限不足!!" : "部分權限不足!!")));
//      }
//    }
//    return;
//  }

//  if (!triggerTexts3.isEmpty()) {
//    String triggerText = triggerTexts3.get(0);
//    RichMenu rm = this.richMenuRepository.getByName(triggerText).orElse(null);
//    if (null != rm) {
//      this.lineRichMenuService.linkRichMenuToUser(userId, rm.getRichMenuId());
//    } else {
//      this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage("找不到指定頁面，請洽管理員。")));
//    }
//    return;
//  }

  }

}