package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Service.LineBotService;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.DataBase.Service.LineUserService;
import com.linecorp.bot.model.profile.UserProfileResponse;

@Controller
@RequestMapping(value = "/line/user")
public class LineUserController {

  @Autowired
  private LineBotService lineBotService;

  @Autowired
  private LineUserService lineUserService;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @GetMapping(value = "/list")
  public ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {
    model = new ModelAndView("layout/line/l_line_user");
    Page<LineUser> users = lineUserService.getAll(pageable);
    model.addObject("users", users.stream().filter(u->!u.getIsLeave()).collect(Collectors.toList()));
    model.addObject("allRichMenu", lineRichMenuService.getAll());
    return model;
  }

  /** autocomplete **/
  @PostMapping(value = "/autocomplete/getByFilter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> getByFilter(String term) {
    List<LineUser> lineUsers = lineUserService.getByFilterAll("%" + term + "%");
    return new ResponseEntity<>(lineUsers, HttpStatus.OK);
  }

  @PostMapping(value = "/reloadLineUserInformation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> reloadLineUserInformation() {
    Map<String, Object> result = new HashMap<>();
    List<LineUser> lineUsers = lineUserService.getByIsLeaveFalse();
    lineUsers = lineUsers.stream().map(user -> {
      String userId = user.getUserId();
      if (StringUtils.isNotBlank(userId)) {
        UserProfileResponse userProfile = lineBotService.getProfileByUserId(userId);
        user.setUserName(userProfile.getDisplayName());
        user.setPictureUrl(userProfile.getPictureUrl());
        user.setStatusMessage(userProfile.getStatusMessage());
        return user;
      }
      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());
    lineUsers = lineUserService.saveAll(lineUsers);
    result.put("status", "success");
    result.put("data", lineUsers);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
