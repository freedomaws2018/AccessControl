package com.example.demo.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.DataBase.Service.MemberService;
import com.example.demo.DataBase.Service.MenuService;

@Component
public class AccessControlTask {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Value("${line.richMenu.unLinkTime}")
  private Integer unLinkTime;

  /**
   * 每分鐘 清除 非管理員 LINE@ RichMenu 選單
   **/
  @Scheduled(cron = "0 */1 * * * *")
  public void unlinkRichMenuTask() {
    List<Member> members = memberService.getAllEffectiveMember();
    members.stream().forEach(member -> {
      String lineUserId = member.getLineUserId();
      Boolean isAdmin = member.getIsAdmin();
      String richMenuId = member.getRichMenuId();
      if (StringUtils.isNotBlank(richMenuId) && !isAdmin) {
        try {
          LocalDateTime richMenuLinkDatetime = member.getRichMenuLinkDateTime();
          if (richMenuLinkDatetime == null) {
            lineRichMenuService.unlinkRichMenuToUser(lineUserId);
          } else {
            Duration duration = Duration.between(richMenuLinkDatetime, LocalDateTime.now());
            if (duration.getSeconds() > unLinkTime) {
              lineRichMenuService.unlinkRichMenuToUser(lineUserId);
            }
          }
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * 每天凌晨三點 清除 選單暫存 <BR>
   * 登入後重新產生
   */
  @Scheduled(cron = "0 0 3 * * *")
  public void reloadMenuTask() {
    menuService.removeAllMenuTemporary();
  }

}
