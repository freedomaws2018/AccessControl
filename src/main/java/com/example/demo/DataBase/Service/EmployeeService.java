package com.example.demo.DataBase.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Repository.EmployeeRepository;
import com.google.common.hash.Hashing;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  public Page<Employee> getAll(Pageable pageable) {
    return employeeRepository.findAll(pageable);
  }

  public Employee loginVerification(String account, String passwd) {
    System.err.println(account + " : " + passwd);
    Employee employee = null;

    employee = employeeRepository.findByAccount(account).orElse(null);

    if (employee == null) {
      throw new LoginException("找不到該用戶");
    }
    String passwd_sha256 = Hashing.sha256()
        .hashString(String.format("%s-%s-%s", "FDCe&9WY@EzVp^D99m", account, passwd), StandardCharsets.UTF_8).toString()
        .toUpperCase(Locale.US);

//    System.err.println(employee.getPassword());
//    System.err.println(passwd_sha256);

    if (!employee.getPassword().equals(passwd_sha256)) {
      throw new LoginException("用戶密碼錯誤");
    }

    return employee;
  }

  private class LoginException extends RuntimeException {

    private static final long serialVersionUID = -8251600280803010569L;

    public LoginException(String message) {
      super(message);
    }

    @Override
    public String getMessage() {
      return "【登入錯誤】" + super.getMessage();
    }

  }

}
