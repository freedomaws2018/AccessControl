package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.PermissionDetail;
import com.example.demo.DataBase.Entity.IdClass.PermissionDetailId;

@Repository
public interface PermissionDetailRepository extends JpaRepository<PermissionDetail, PermissionDetailId> {

  List<PermissionDetail> findByTypeIn(List<String> types);

	List<PermissionDetail> findByPermissionId(Long id);

}
