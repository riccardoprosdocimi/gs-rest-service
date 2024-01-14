package com.example.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RestServiceApplicationTests {
	@Mock
	private EmployeeManager employeeManager;
	@InjectMocks
	private EmployeeController employeeController;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetEmployees() throws Exception {
		Employees employees = new Employees();
		employees.getEmployeeList().add(new Employee(
						1,
						"John",
						"Doe",
						"john.doe@hpe.com",
						"Software Engineer"
						)
		);
		employees.getEmployeeList().add(new Employee(
						2,
						"Mary",
						"Johnson",
						"mary.johnson@hpe.com",
						"Marketing Manager"
						)
		);
		employees.getEmployeeList().add(new Employee(
						3,
						"Alex",
						"Smith",
						"alex.smith@hpe.com",
						"Project Manager"
						)
		);
		// Mocking the behavior of EmployeeManager
		when(this.employeeManager.getAllEmployees()).thenReturn(employees);

		// Performing GET request and verifying the result
		mockMvc.perform(MockMvcRequestBuilders
										.get("/employees")
										.contentType(MediaType.APPLICATION_JSON)
						)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList", Matchers.hasSize(3)))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[0].id", Matchers.is(1)))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[0].firstName", Matchers.is("John")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[0].lastName", Matchers.is("Doe")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[0].email", Matchers.is("john.doe@hpe.com")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[0].title", Matchers.is("Software Engineer")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[1].id", Matchers.is(2)))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[1].firstName", Matchers.is("Mary")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[1].lastName", Matchers.is("Johnson")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[1].email", Matchers.is("mary.johnson@hpe.com")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[1].title", Matchers.is("Marketing Manager")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[2].id", Matchers.is(3)))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[2].firstName", Matchers.is("Alex")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[2].lastName", Matchers.is("Smith")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[2].email", Matchers.is("alex.smith@hpe.com")))
						.andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[2].title", Matchers.is("Project Manager")));
	}

	@Test
	void testAddEmployee() throws Exception {
		Employees employees = new Employees();
		employees.getEmployeeList().add(new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer"
						)
		);
		employees.getEmployeeList().add(new Employee(
						2,
						"John",
						"Doe",
						"john.doe@hpe,com",
						"Software Engineer"
						)
		);
		employees.getEmployeeList().add(new Employee(
						3,
						"Mary",
						"Johnson",
						"mary.johnson@hpe.com",
						"Marketing Manager"
						)
		);
		employees.getEmployeeList().add(new Employee(
						4,
						"Alex",
						"Smith",
						"alex.smith@hpe,com",
						"Project Manager"
						)
		);

		// Mocking the behavior of EmployeeManager
		when(employeeManager.getAllEmployees()).thenReturn(employees);

		// Creating a mock Employee object
		Employee mockEmployee = new Employee(
						null,
						"MockFirstName",
						"MockLastName",
						"mock@example.com",
						"MockTitle"
		);

		// Performing POST request and verifying the result
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
										.content(objectMapper.writeValueAsString(mockEmployee))
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isCreated())
						.andExpect(MockMvcResultMatchers.header().exists("Location"));
	}

	// Helper function to get the number of current employees
	int getEmployeeCount(EmployeeManager manager)  {
		return manager.getAllEmployees().getEmployeeList().size();
	}

	@Test
	void createEmployeeManager() {
		// Ensure that employee list is populated on initialization
		EmployeeManager newEmployeeManager = new EmployeeManager();
		assert(getEmployeeCount(newEmployeeManager) != 0);
	}

	@Test
	void addEmployee() {
		// Ensure that adding an employee increases the employee count by 1
		EmployeeManager employeeManager = new EmployeeManager();
		int employeeCount = getEmployeeCount(employeeManager);
		Employee employee = new Employee(
						3,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer"
		);
		employeeManager.addEmployee(employee);
		assert(employeeCount + 1 == getEmployeeCount(employeeManager));
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	void employeeIdInList() {
		// Check whether added employee ID is found in ID field
		this.employeeManager = new EmployeeManager();
		Employee newEmployee = new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer");
		this.employeeManager.addEmployee(newEmployee);
		List<Employee> employees = this.employeeManager.getAllEmployees().getEmployeeList();
    for (Employee employee : employees) {
      if (employee.getId() == 1) {
        return;
      }
    }
		assert(false);
	}

	@Test
	void employeeFirstNameInList() {
		// Check whether added employee first name is found in first name field
		this.employeeManager = new EmployeeManager();
		Employee newEmployee = new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer");
		this.employeeManager.addEmployee(newEmployee);
		List<Employee> employees = this.employeeManager.getAllEmployees().getEmployeeList();
    for (Employee employee : employees) {
      if (Objects.equals(employee.getFirstName(), "Daria")) {
        return;
      }
    }
		assert(false);
	}

	@Test
	void employeeLastNameInList() {
		// Check whether added employee last name is found in last name field
		this.employeeManager = new EmployeeManager();
		Employee newEmployee = new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer");
		this.employeeManager.addEmployee(newEmployee);
		List<Employee> employees = this.employeeManager.getAllEmployees().getEmployeeList();
    for (Employee employee : employees) {
      if (Objects.equals(employee.getLastName(), "Jones")) {
        return;
      }
    }
		assert(false);
	}

	@Test
	void employeeEmailInList() {
		// Check whether added employee email is found in email field
		this.employeeManager = new EmployeeManager();
		Employee newEmployee = new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer");
		this.employeeManager.addEmployee(newEmployee);
		List<Employee> employees = this.employeeManager.getAllEmployees().getEmployeeList();
    for (Employee employee : employees) {
      if (Objects.equals(employee.getEmail(), "dariajones@gmail.com")) {
        return;
      }
    }
		assert(false);
	}

	@Test
	void employeeTitleInList() {
		// Check whether added employee title is found in title field
		this.employeeManager = new EmployeeManager();
		Employee newEmployee = new Employee(
						1,
						"Daria",
						"Jones",
						"dariajones@gmail.com",
						"Software developer");
		this.employeeManager.addEmployee(newEmployee);
		List<Employee> employees = this.employeeManager.getAllEmployees().getEmployeeList();
    for (Employee employee : employees) {
      if (Objects.equals(employee.getTitle(), "Software developer")) {
        return;
      }
    }
		assert(false);
	}
}
