package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> getById(Long id);

  Optional<Member> getByLineUserIdAndIsUseTrue(String lineUserId);

  List<Member> getByRichMenuId(String richMenuId);

  @Transactional
  @Query(value = "SELECT * FROM tbl_member WHERE is_use = true AND NOW() BETWEEN beg_dt AND end_dt ; ", nativeQuery = true)
  List<Member> getAllEffectiveMember();

//  @Query(value = "SELECT * FROM tbl_member WHERE is_use = true AND NOW() BETWEEN beg_dt AND end_dt AND line_user_id = :lineUserId ; ", nativeQuery = true)
//  Optional<Member> getByIsUseTrueAndEffectiveAndUserId(@Param("lineUserId") String lineUserId);

  @Transactional
  @Query(value = "SELECT * FROM tbl_member WHERE 1 = 1 AND is_use = true AND NOW() BETWEEN beg_dt AND end_dt AND line_user_id = :lineUserId ;", nativeQuery = true)
  Optional<Member> getEffectiveMember(@Param("lineUserId") String lineUserId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE tbl_member SET rich_menu_link_datetime = now() WHERE line_user_id = :lineUserId ; ", nativeQuery = true)
  void updateRichMenuLinkDatetime(@Param("lineUserId") String lineUserId);

}
