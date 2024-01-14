package com.example.restservice;

import org.springframework.stereotype.Service;

@Service
public class EmployeeManager {
  private static final Employees list = new Employees();
  static {
    list.getEmployeeList().add(new Employee(
            1,
            "John",
            "Doe",
            "john.doe@hpe.com",
            "Software Engineer"
            )
    );
    list.getEmployeeList().add(new Employee(
            2,
            "Mary",
            "Johnson",
            "mary.johnson@hpe.com",
            "Marketing Manager"
            )
    );
    list.getEmployeeList().add(new Employee(
            3,
            "Alex",
            "Smith",
            "alex.smith@hpe.com",
            "Project Manager"
            )
    );
  }

  public Employees getAllEmployees() {
    return list;
  }

  public void addEmployee(Employee employee) {
    list.getEmployeeList().add(employee);
  }
}
