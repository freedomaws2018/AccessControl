package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.demo.Common.HttpUtils;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.DataBase.Repository.RichMenuTemplateRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.richmenu.RichMenu;
import com.linecorp.bot.model.richmenu.RichMenuIdResponse;
import com.linecorp.bot.model.richmenu.RichMenuListResponse;
import com.linecorp.bot.model.richmenu.RichMenuResponse;

@Service
//@PropertySource("classpath:config/linebot.yml")
public class LineRichMenuService {

  @Value("${line.bot.channelToken}")
  private String channelAccessToken;

  @Autowired
  private MemberService memberService;

  @Autowired
  private LocationService LocationService;

  @Autowired
  private RichMenuRepository richMenuRepository;

  @Autowired
  private RichMenuTemplateRepository richMenuTemplateRepository;

  public com.example.demo.DataBase.Entity.RichMenu save(com.example.demo.DataBase.Entity.RichMenu richMenu) {
    return richMenuRepository.save(richMenu);
  }

  public List<com.example.demo.DataBase.Entity.RichMenu> save(
      List<com.example.demo.DataBase.Entity.RichMenu> richMenus) {
    return richMenuRepository.saveAll(richMenus);
  }

  public List<com.example.demo.DataBase.Entity.RichMenu> getAll() {
    return richMenuRepository.findAll();
  }

  public com.example.demo.DataBase.Entity.RichMenu getByRichMenuId(String richMneuId) {
    return richMenuRepository.findById(richMneuId).orElse(null);
  }

//  private final static String getRichMenuUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId";

  public RichMenuResponse getRichMenu(String richMenuId) throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    RichMenuResponse richMenuResponse = client.getRichMenu(richMenuId).get();
    return richMenuResponse;
  }

//  private final static String getRichMenuIdLinkToUserUrl = "https://api.line.me/v2/bot/user/:userId/richmenu";

  public RichMenuIdResponse getRichMenuIdLinkToUser(String userId) throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    RichMenuIdResponse richMenuIdResponse = client.getRichMenuIdOfUser(userId).get();
    return richMenuIdResponse;
  }

//  private final static String getRichMenuListUrl = "https://api.line.me/v2/bot/richmenu/list";

  public RichMenuListResponse getRichMenuList() throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    RichMenuListResponse richMenuResponse = client.getRichMenuList().get();
    return richMenuResponse;
  }

//  private final static String deleteRichMenuUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId";

  public BotApiResponse deleteRichMenu(String richMenuId) throws InterruptedException, ExecutionException {

    /** 刪除 資料庫 資料 **/
    richMenuRepository.deleteById(richMenuId);

    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    BotApiResponse botApiResponse = client.deleteRichMenu(richMenuId).get();

    return botApiResponse;
  }

  private final static String downloadImageUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId/content";

  public byte[] downloadImage(String richMenuId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", String.format("Bearer %s", this.channelAccessToken));
    byte[] image = null;
    try {
      image = HttpUtils.doGetImage(headers, downloadImageUrl.replace(":richMenuId", richMenuId));
    } catch (Exception ex) {

    }
    return image;
  }

  public String downloadImageBase64(String richMenuId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", String.format("Bearer %s", this.channelAccessToken));
    String imageBase64 = null;
    try {
      imageBase64 = HttpUtils.doGetImageBase64(headers, downloadImageUrl.replace(":richMenuId", richMenuId));
    } catch (Exception ex) {

    }
    return imageBase64;
  }

//  private final static String createRichMenuUrl = "https://api.line.me/v2/bot/richmenu";

  public RichMenuIdResponse createRichMenu(RichMenu richMenu) throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    RichMenuIdResponse response = client.createRichMenu(richMenu).get();
    return response;
  }

//  private final static String uploadRichMenuImageUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId/content";

  public BotApiResponse uploadRichMenuImage(String richMenuId, byte[] image)
      throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    BotApiResponse response = client.setRichMenuImage(richMenuId, "image/png", image).get();
    return response;
  }

//  private final static String linkRichMenuToUserUrl = "https://api.line.me/v2/bot/user/:lineUserId/richmenu/:richMenuId";

  public BotApiResponse linkRichMenuToUser(String lineUserId, String richMenuId)
      throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    BotApiResponse botApiResponse = client.linkRichMenuIdToUser(lineUserId, richMenuId).get();
    return botApiResponse;
  }

//	Unlink rich menu from user
//  private final static String unlinkRichMenuToUserUrl = "https://api.line.me/v2/bot/user/:lineUserId/richmenu";

  public BotApiResponse unlinkRichMenuToUser(String lineUserId) throws InterruptedException, ExecutionException {
    LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
    BotApiResponse botApiResponse = client.unlinkRichMenuIdFromUser(lineUserId).get();
    return botApiResponse;
  }

}
