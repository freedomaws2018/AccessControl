package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

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

  public Member save(Member member) {
    return memberRepository.saveAndFlush(member);
  }

  public void deleteById(Long memberId) {
    memberRepository.deleteById(memberId);
  }

}
