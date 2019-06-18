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

@Component
public class LineUserTask {

  @Autowired
  private MemberService memberService;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Value("${line.richMenu.unLinkTime}")
  private Integer unLinkTime;

  @Scheduled(cron = "0 */10 * * * *")
//  @Scheduled(cron = "*/30 * * * * *")
  public void taskUnlinkRichMenu() {
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

}
