package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Common.HttpUtils;
import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Entity.Log.LogIot;
import com.example.demo.DataBase.Repository.LogIotRepository;
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
  private MemberService memberService;

  @Autowired
  private LogIotRepository logIotRepository;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Autowired
  private RichMenuService richMenuService;

  @Autowired
  private Wf8266Service wf8266Service;

  @Value("${line.bot.channelToken}")
  private String channelAccessToken;

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
//    System.err.println("triggerTexts1" + triggerTexts);
    // 指令開頭過濾，指令開頭均為 #
    List<String> triggerTexts1 = triggerTexts.stream() //
        .filter(triggerText -> triggerText.matches("^#.*")) // 單個#號開頭
        .map(triggerText -> triggerText.substring(1)) // 去除第一個#
        .collect(Collectors.toList());

    if (triggerTexts1 == null || triggerTexts1.isEmpty()) {
      return null;
    }

    // 觸動指令 ( 雙#開頭 )
    List<String> triggerTexts2 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^#.*"))
        .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

    // 換頁指令 ( #>開頭 )
    List<String> triggerTexts3 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^>.*"))
        .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());
//    System.err.println("triggerTexts2" + triggerTexts2);
//    System.err.println("triggerTexts3" + triggerTexts3);
//    return null;

    Member member = null;
    // 1. 判斷指令存在
    if (!triggerTexts2.isEmpty() || !triggerTexts3.isEmpty()) {
      member = memberService.getEffectiveMember(userId);
      // 2. 判斷使用者存在
      if (member == null) {
        return "使用者沒有權限";
      }
    }

    if (!triggerTexts2.isEmpty()) {
      String SN = triggerTexts2.get(0).split("_")[0];
      String WDN = triggerTexts2.get(0).split("_")[1];
      Wf8266Detail detail = wf8266Service.getWf8266DetailBySnAndName(SN, WDN);
      String url = detail.getTriggerUrl();
      HttpUtils.doGet(url);

      // TODO : save Log
      LogIot log = new LogIot(member.getId(), detail.getWf8266().getLocationId(), SN, WDN);
      logIotRepository.saveAndFlush(log);

      return detail.getReply2();
    }

    if (!triggerTexts3.isEmpty()) {
      String triggerText = triggerTexts3.get(0);
      RichMenu rm = richMenuService.getByName(triggerText);
      if (null != rm) {
        String UI = member.getLineUserId();
        String RMI = rm.getRichMenuId();
        try {
          lineRichMenuService.linkRichMenuToUser(UI, RMI);
          return triggerText;
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      } else {
        return "找不到指定頁面，請洽管理員。";
      }
    }

    return null;
  }

}
