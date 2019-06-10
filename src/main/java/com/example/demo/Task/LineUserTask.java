package com.example.demo.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.DataBase.Service.LineUserService;

@Component
public class LineUserTask {

  @Autowired
  private LineUserService lineUserService;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Scheduled(cron = "0 */10 * * * *")
  public void test() {
    List<LineUser> lineUsers = lineUserService.getAllLineUserByEffectiveAndUserId();
    if (lineUsers != null && !lineUsers.isEmpty()) {
      lineUsers.stream().forEach(lineUser -> {
        String lineUserId = lineUser.getUserId();
        Boolean isAdmin = lineUser.getIsAdmin();
        String richMenuId = lineUser.getRichMenuId();
        if (StringUtils.isNotBlank(richMenuId) && !isAdmin) {
          try {
            LocalDateTime richMenuLinkDatetime = lineUser.getRichMenuLinkDateTime();
            Duration duration = Duration.between(richMenuLinkDatetime, LocalDateTime.now());
            if (duration.toMinutes() > 10) {
              lineRichMenuService.unlinkRichMenuToUser(lineUserId);
            }
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

}
