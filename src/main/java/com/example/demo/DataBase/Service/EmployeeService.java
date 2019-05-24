package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Repository.EmployeeRepository;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public List<Employee> getAll(){
    return employeeRepository.findAll();
  }

  public Page<Employee> getAll(Pageable pageable){
    return employeeRepository.findAll(pageable);
  }

}
