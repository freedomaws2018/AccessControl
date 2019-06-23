package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  public List<Member> getAll() {
    return memberRepository.findAll();
  }

  public Page<Member> getAll(Pageable pageable) {
    return memberRepository.findAll(pageable);
  }

  public Member getById(Long memberId) {
    if (memberId == null) {
      return null;
    }
    return memberRepository.findById(memberId).orElse(null);
  }

  public List<Member> getByIds(List<Long> memberIds) {
    return memberRepository.findAllById(memberIds);
  }

  public List<Member> getByRichMenuId(String richMenuId) {
    return memberRepository.getByRichMenuId(richMenuId);
  }

  public Member save(Member member) {
    return memberRepository.saveAndFlush(member);
  }

  public List<Member> saveAll(List<Member> members) {
    List<Member> ms = memberRepository.saveAll(members);
    memberRepository.flush();
    return ms;
  }

  public void deleteById(Long memberId) {
    memberRepository.deleteById(memberId);
  }

  public List<Member> getAllEffectiveMember() {
    return memberRepository.getAllEffectiveMember();
  }

  public Member getEffectiveMember(String lineUserId) {
    return memberRepository.getEffectiveMember(lineUserId).orElse(null);
  }

  public Member getByUserIdAndIsUseTrue(String lineUserId) {
    return memberRepository.getByLineUserIdAndIsUseTrue(lineUserId).orElse(null);
  }

  public Member setRichMneuByUserId(String lineUserId) {
    Member member = getEffectiveMember(lineUserId);
    if (member != null) {
      LineUser lineUser = member.getLineUser();
      if (lineUser != null) {
        String richMenuId = member.getRichMenuId();
        if (StringUtils.isNotBlank(richMenuId)) {
          try {
            lineRichMenuService.linkRichMenuToUser(lineUserId, richMenuId);
            memberRepository.updateRichMenuLinkDatetime(lineUserId);
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return member;
  }

}
