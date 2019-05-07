package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.Common.HttpUtils;
import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.LogWf8266;
import com.example.demo.DataBase.Entity.MappingWf8266DetailAndUser;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.LogWf8266Repository;
import com.example.demo.DataBase.Repository.MappingWf8266DetailAndUserRepository;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.DataBase.Repository.Wf8266DetailRepository;
import com.example.demo.DataBase.Repository.Wf8266Repository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
//@PropertySource("classpath:config/linebot.yml")
public class LineBotApplication {

	private static Logger logger = LoggerFactory.getLogger(LineBotApplication.class);

	@Autowired
	private LineRichMenuService lineRichMenuService;

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private LogWf8266Repository loggerWf8266Repository;

	@Autowired
	private Wf8266Repository wf8266Repository;

	@Autowired
	private Wf8266DetailRepository wf8266DetailRepository;

	@Autowired
	private MappingWf8266DetailAndUserRepository mappingWf8266DetailAndUserRepository;

	@Autowired
	private RichMenuRepository richMenuRepository;

//	@Autowired
//	private LoggerWf8266Repository loggerWf8266Repository;

	@Value("${line.bot.channelToken}")
	private String channelAccessToken;

	@Value("${line.GetProfileUrl}")
	private String getProfileUrl;

	/** 加入/解除封鎖 時 **/
	@EventMapping
	public void handleFollowEvent(FollowEvent event) {
		String userId = event.getSource().getUserId();
		UserProfileResponse userProfile = this.getProfileByUserId(userId);
		String userName = userProfile.getDisplayName();

		LineUser lineUser = this.lineUserRepository.getByUserId(userId).orElse(null);
		if (lineUser == null) {
			lineUser = new LineUser();
			lineUser.setCreateDate(LocalDateTime.now(ZoneId.of("UTC+8")));
			lineUser.setUserName(userName);
			lineUser.setUserId(userId);
			lineUser.setBegDt(LocalDateTime.now());
			lineUser.setEndDt(LocalDateTime.now().plusYears(1l));
			lineUser.setIsUse(true);
		} else {
			lineUser.setIsUse(true);
		}
		this.lineUserRepository.save(lineUser);
		this.doReplyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage(userProfile.getDisplayName() + " - 註冊成功")));
		logger.info("【註冊】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
	}

	/** 移除/封鎖 時 **/
	@EventMapping
	public void handleUnfollowEvent(UnfollowEvent event) {
		String userId = event.getSource().getUserId();
		LineUser lineUser = this.lineUserRepository.getByUserIdAndIsUseTrue(userId).orElse(null);

		if (lineUser != null) {
			lineUser.setIsUse(false);
			this.lineUserRepository.save(lineUser);
		}
		logger.info("【封鎖】\t" + lineUser.getUserId() + "\t" + lineUser.getUserName());
	}

	@EventMapping
	public void handlePostbackEvent(PostbackEvent event) {
		try {
			String replyToken = event.getReplyToken();
			String userId = event.getSource().getUserId();
			String text = event.getPostbackContent().getData();
			List<String> triggerTexts = Arrays.asList(text.split(","));
			this.wf8266Handle(replyToken, userId, triggerTexts);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

//	@EventMapping
//	public void handleTextMessageEvent(MessageEvent<MessageContent> event) {
//		// 1. 取得 Message 類別
//		MessageContent message = event.getMessage();
//		// 2. 確認 Message 種類 ( 只處理 文字類 <TextMessageContext> )
//		try {
//			if (message instanceof TextMessageContent) {
//				String replyToken = event.getReplyToken();
//				String userId = event.getSource().getUserId();
//				String text = ((TextMessageContent) event.getMessage()).getText();
//				List<String> triggerTexts = Arrays.asList(text.split(","));
//				wf8266Handle(replyToken, userId, triggerTexts);
//			}
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//		}
//	}

	private void wf8266Handle(String replyToken, String userId, List<String> triggerTexts) {
		System.err.println(triggerTexts);
		// 指令開頭過濾，指令開頭均為 #
		List<String> triggerTexts1 = triggerTexts.stream().filter(triggerText -> triggerText.matches("^#.*"))
		    .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

		if (triggerTexts1 == null || triggerTexts1.isEmpty()) {
			return;
		}

		// 觸動指令
		List<String> triggerTexts2 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^#.*"))
		    .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

		// 換頁指令
		List<String> triggerTexts3 = triggerTexts1.stream().filter(triggerText -> triggerText.matches("^>.*"))
		    .map(triggerText -> triggerText.substring(1)).collect(Collectors.toList());

		LineUser lineUser = null;
		if (!triggerTexts2.isEmpty() || !triggerTexts3.isEmpty()) {
			// 1. 有指令需要執行前 先確認使用者權限
			lineUser = this.lineUserRepository.getEffectiveUser(userId).orElse(null);
			if (lineUser == null) {
				this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage("使用者沒有權限")));
				return;
			}
		}

		if (!triggerTexts2.isEmpty()) {
			List<Wf8266Detail> wDetails = this.wf8266DetailRepository.getByTriggerTextInAndIsUseTrue(triggerTexts2);

			if (wDetails != null && !wDetails.isEmpty()) {
				//
				List<MappingWf8266DetailAndUser> mappingWdUs = this.mappingWf8266DetailAndUserRepository
				    .getByIsUseTrueAndUserIdAndTriggerTextIn(userId,
				        wDetails.stream().map(Wf8266Detail::getTriggerText).collect(Collectors.toList()));

				if (wDetails.size() == mappingWdUs.size()) { // 所以指令均有權限
					wDetails.stream().map(Wf8266Detail::getTriggerUrl).forEach(HttpUtils::doGet);
					List<String> replys = wDetails.stream().map(Wf8266Detail::getReply).collect(Collectors.toList());
//					System.err.println(lineUser.getUserName() + ":" + String.join("\r\n", replys));

					new Thread(() -> {
						triggerTexts.forEach(triggerText -> {
							this.loggerWf8266Repository.save(new LogWf8266(userId, triggerText));
						});
					}).start();

					logger.info("【指令】 " + lineUser.getUserName() + ":" + String.join("\r\n", replys));
					this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage(String.join("\r\n", replys))));
				} else {
					this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage(mappingWdUs.isEmpty() ? "權限不足!!" : "部分權限不足!!")));
				}
			}
			return;
		}

		if (!triggerTexts3.isEmpty()) {
			String triggerText = triggerTexts3.get(0);
			RichMenu rm = this.richMenuRepository.getByName(triggerText).orElse(null);
			if (null != rm) {
				this.lineRichMenuService.linkRichMenuToUser(userId, rm.getRichMenuId());
			} else {
				this.doReplyMessage(new ReplyMessage(replyToken, new TextMessage("找不到指定頁面，請洽管理員。")));
			}
			return;
		}

	}

	/** 回應訊息 **/
	@SuppressWarnings("unused")
//ReplyMessage replyMessage = new ReplyMessage(replyToken, new TextMessage(text));
//doReplyMessage(replyMessage);
	private BotApiResponse doReplyMessage(ReplyMessage replyMessage) {
		LineMessagingClient client = LineMessagingClient.builder(this.channelAccessToken).build();
		try {
			return client.replyMessage(replyMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			return null;
		}
	}

	/** 發送訊息 **/
//PushMessage pushMessage = new PushMessage(event.getSource().getUserId(), new TextMessage("Push"));
//doPushMessage(pushMessage);
	@SuppressWarnings("unused")
	private BotApiResponse doPushMessage(PushMessage pushMessage) {
		LineMessagingClient client = LineMessagingClient.builder(this.channelAccessToken).build();
		try {
			return client.pushMessage(pushMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			return null;
		}
	}

	/** 取得用戶資料 By UserId **/
	private UserProfileResponse getProfileByUserId(String userId) {
		LineMessagingClient client = LineMessagingClient.builder(this.channelAccessToken).build();

		try {
			return client.getProfile(userId).get();
		} catch (InterruptedException | ExecutionException e1) {
			return null;
		}

	}

//	/** GET 請求 URL **/
//	private String doGetUrl(String URL) {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Accept", "*/*");
//
//		ResponseEntity<String> responseEntity = new RestTemplate().exchange(URL, HttpMethod.GET,
//		    new HttpEntity<>("", headers), String.class);
//
//		return responseEntity.getBody();
//	}
//
//	/** POST 請求 URL **/
//	private String doPostUrl(String URL) {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
//
//		ResponseEntity<String> responseEntity = new RestTemplate().exchange(URL, HttpMethod.POST,
//		    new HttpEntity<>("", headers), String.class);
//
//		return responseEntity.getBody();
//	}

}