package com.example.demo.DataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
