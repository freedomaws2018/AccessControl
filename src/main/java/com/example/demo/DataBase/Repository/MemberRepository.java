package com.example.demo.DataBase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<LineUser> getById(Long id);

//  Optional<LineUser> getByUserIdAndIsUseTrue(Long id);
//
//  List<LineUser> getByUserIdIn(List<Long> ids);

//  @Query(value = "SELECT * FROM tbl_line_user WHERE is_use = true AND NOW() BETWEEN beg_dt AND end_dt ; ", nativeQuery = true)
//  List<LineUser> getByIsUseTrueAndEffective();
//
//  @Query(value = "SELECT * FROM tbl_line_user WHERE is_use = true AND NOW() BETWEEN beg_dt AND end_dt AND user_id = :userId ; ", nativeQuery = true)
//  Optional<LineUser> getByIsUseTrueAndEffectiveAndUserId(@Param("userId") String userId);
//
//  @Query(value = "SELECT * FROM tbl_line_user WHERE 1 = 1 AND user_name LIKE ?1 ;", nativeQuery = true)
//  List<LineUser> getByUserNameLike(String userName);
//
//  @Query(value = "SELECT * FROM tbl_line_user WHERE 1 = 1 AND is_use = true AND NOW() BETWEEN beg_dt AND end_dt AND user_id = ?1 ;", nativeQuery = true)
//  Optional<LineUser> getEffectiveUser(String userId);
//
//  @Modifying
//  @Transactional
//  @Query(value = "UPDATE tbl_line_user SET rich_menu_link_datetime = now() WHERE user_id = :userId ; ", nativeQuery = true)
//  void updateRichMenuLinkDatetime(@Param("userId") String userId);

}
