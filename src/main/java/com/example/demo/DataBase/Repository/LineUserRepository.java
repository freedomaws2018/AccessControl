package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Lob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.LineUser;

@Repository
public interface LineUserRepository extends JpaRepository<LineUser, String> {

  Optional<LineUser> getByUserId(String userId);

  @Lob
  @Transactional
  @Query(value = "SELECT * FROM tbl_line_user WHERE user_id like :term OR user_name like :term ; ", nativeQuery = true)
  List<LineUser> getByFilterAll(@Param("term") String term);

//  Optional<LineUser> getByUserIdAndIsUseTrue(String userId);
//
//  List<LineUser> getByUserIdIn(List<String> userIds);
//
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


}
