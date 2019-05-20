package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.PermissionDetail;

@Repository
public interface PermissionDetailRepository extends JpaRepository<PermissionDetail, String> {

	List<PermissionDetail> findByPermissionId(Long id);

}
