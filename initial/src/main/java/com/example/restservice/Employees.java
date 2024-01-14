package com.example.restservice;

import java.util.ArrayList;
import java.util.List;

public class Employees {
  private List<Employee> employeeList;

  public List<Employee> getEmployeeList() {
    if (this.employeeList == null) {
      this.employeeList = new ArrayList<>();
    }
    return this.employeeList;
  }

  public void setEmployeeList(List<Employee> employeeList) {
    this.employeeList = employeeList;
  }
}
