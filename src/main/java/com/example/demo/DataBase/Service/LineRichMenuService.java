package com.example.demo.DataBase.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.demo.Common.HttpUtils;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.LineModel.RichMenu.RichMenu;
import com.example.demo.LineModel.RichMenu.RichMenuResponse;
import com.google.gson.Gson;

@Service
//@PropertySource(value = "config/linebot.yml")
public class LineRichMenuService {

	@Value("${line.bot.channelToken}")
	private String channelAccessToken;

	@Autowired
	private RichMenuRepository richMenuRepository;

	private final static String getRichMenuUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId";

	public RichMenu getRichMenu(String richMenuId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		RichMenu response = HttpUtils.doGet(headers, getRichMenuUrl.replace(":richMenuId", richMenuId), RichMenu.class);
		return response;
	}

	private final static String getRichMenuIdLinkToUserUrl = "https://api.line.me/v2/bot/user/:userId/richmenu";

	public String getRichMenuIdLinkToUser(String userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		String responseJson = HttpUtils.doGet(headers, getRichMenuIdLinkToUserUrl.replace(":userId", userId), String.class);
		Map<String, String> responseMap = new Gson().fromJson(responseJson, Map.class);
		String result = responseMap.get("richMenuId");

		return result;
	}

	private final static String getRichMenuListUrl = "https://api.line.me/v2/bot/richmenu/list";

	public RichMenuResponse getRichMenuList() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		RichMenuResponse response = HttpUtils.doGet(headers, getRichMenuListUrl, RichMenuResponse.class);
		return response;
	}

	private final static String deleteRichMenuUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId";

	public void deleteRichMenu(String richMenuId) {
		/** 刪除 LineServer 資料 **/
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		HttpUtils.doDelete(headers, deleteRichMenuUrl.replace(":richMenuId", richMenuId));

		/** 刪除 資料庫 資料 **/
		richMenuRepository.deleteById(richMenuId);
	}

	private final static String downloadImageUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId/content";

	public byte[] downloadImage(String richMenuId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		byte[] image = null;
		try {
			image = HttpUtils.doGetImage(headers, downloadImageUrl.replace(":richMenuId", richMenuId));
		} catch (Exception ex) {

		}
		return image;
	}

	public String downloadImageBase64(String richMenuId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		String imageBase64 = null;
		try {
			imageBase64 = HttpUtils.doGetImageBase64(headers, downloadImageUrl.replace(":richMenuId", richMenuId));
		} catch (Exception ex) {

		}
		return imageBase64;
	}

	private final static String createRichMenuUrl = "https://api.line.me/v2/bot/richmenu";

	public String createRichMenu(String richMenuJson) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		headers.add("Content-Type", "application/json");
		Map responseMap = HttpUtils.doPostWithBody(headers, createRichMenuUrl, richMenuJson, Map.class);
		String richMenuId = responseMap.get("richMenuId").toString();
		return richMenuId;
	}

	private final static String uploadRichMenuImageUrl = "https://api.line.me/v2/bot/richmenu/:richMenuId/content";

	public Boolean uploadRichMenuImage(String richMenuId, byte[] image) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		headers.add("Content-Type", "image/png");

		return HttpUtils.doPostWithImage(headers, uploadRichMenuImageUrl.replace(":richMenuId", richMenuId), image);
	}

	private final static String linkRichMenuToUserUrl = "https://api.line.me/v2/bot/user/:lineUserId/richmenu/:richMenuId";

	public Boolean linkRichMenuToUser(String lineUserId, String richMenuId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		int statusCode = HttpUtils.doPost(headers,
		    linkRichMenuToUserUrl.replace(":lineUserId", lineUserId).replace(":richMenuId", richMenuId));
		return statusCode == 200;
	}

//	Unlink rich menu from user
	private final static String unlinkRichMenuToUserUrl = "https://api.line.me/v2/bot/user/:lineUserId/richmenu";

	public Boolean unlinkRichMenuToUser(String lineUserId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));
		int statusCode = HttpUtils.doDelete(headers, unlinkRichMenuToUserUrl.replace(":lineUserId", lineUserId));
		return statusCode == 200;
	}

}