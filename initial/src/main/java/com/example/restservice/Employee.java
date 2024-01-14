package com.example.restservice;

public class Employee {
  private Integer employeeId;
  private String firstName;
  private String lastName;
  private String email;
  private String title;

  public Employee(Integer employeeId, String firstName, String lastName, String email, String title) {
    this.employeeId = employeeId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.title = title;
  }

  @Override
  public String toString() {
    return "Employee: ID = " + this.employeeId
            + ", First Name = " + this.firstName
            + ", Last Name = " + this.lastName
            + ", email = " + this.email
            + ", title = " + this.title;
  }

  public Integer getId() {
    return this.employeeId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getTitle() {
    return this.title;
  }

  public void setId(Integer id) {
    this.employeeId = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
