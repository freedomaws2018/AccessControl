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

  @Transactional
  Optional<LineUser> getByUserId(String userId);

  @Transactional
  List<LineUser> getByIsLeaveFalse();

  @Lob
  @Transactional
  @Query(value = "SELECT * FROM tbl_line_user WHERE user_id like :term OR user_name like :term ; ", nativeQuery = true)
  List<LineUser> getByFilterAll(@Param("term") String term);

}
