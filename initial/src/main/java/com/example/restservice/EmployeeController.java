package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class EmployeeController {
  private final EmployeeManager employeeManager;

  public EmployeeController(EmployeeManager employeeManager) {
    this.employeeManager = employeeManager;
  }

  @GetMapping(
          path = "/employees",
          produces = "application/json"
  )
  public Employees getEmployees() {
    return this.employeeManager.getAllEmployees();
  }

  @PostMapping(
          path = "/employees",
          consumes = "application/json",
          produces = "application/json"
  )
  public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
    Integer id = employeeManager.getAllEmployees().getEmployeeList().size() + 1;
    employee.setId(id);
    employeeManager.addEmployee(employee);
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(employee.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
