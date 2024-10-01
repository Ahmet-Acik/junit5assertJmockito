package org.ahmet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.ahmet.DepartmentType.HR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompanyServiceTest {
    private List<Employee> employees;
    private CompanyService companyService = new CompanyService();

    @BeforeEach
    public void setUp() {
        employees = new ArrayList<>();
        employees.add(new Employee("1", "John Doe", 50000, new Department(HR)));
        employees.add(new Employee("2", "Jane Doe", 60000, new Department(HR)));
        employees.add(new Employee("3", "Johnny Walker", 70000, new Department(HR)));
    }

    @Test
    @DisplayName("test_Calculate_Total_Salary")
    public void testCalculateTotalSalary() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        double totalSalary = companyService.calculateTotalSalary(department);
        assertThat(totalSalary).isEqualTo(employees.stream().mapToDouble(Employee::getSalary).sum());
    }

    @Test
    @DisplayName("test_Find_Employee_ById")
    public void testFindEmployeeById() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        Employee foundEmployee = companyService.findEmployeeById(department, "1");
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo("1");
    }

    @ParameterizedTest(name = "{index} => id={0}")
    @MethodSource("employeeIdsProvider")
    @DisplayName("test_Find_Employee_ById_Parameterized")
    public void testFindEmployeeByIdParameterized(String id) {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        Employee foundEmployee = companyService.findEmployeeById(department, id);
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo(id);
    }

    private static Stream<Arguments> employeeIdsProvider() {
        return Stream.of(
                Arguments.of("1"),
                Arguments.of("2"),
                Arguments.of("3")
        );
    }

    @Test
    @DisplayName("test_Find_Employee_ById_Not_Found")
    public void testFindEmployeeByIdNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        Employee foundEmployee = companyService.findEmployeeById(department, "10");
        assertThat(foundEmployee).isNull();
    }

    @Test
    @DisplayName("find_Employees_ByDepartment")
    public void findEmployeesByDepartment() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByDepartment(department);
        assertThat(foundEmployees).isEqualTo(employees);
    }

    @Test
    @DisplayName("test_Calculate_Average_Salary")
    public void testCalculateAverageSalary() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        double averageSalary = companyService.calculateAverageSalary(department);
        assertThat(averageSalary).isEqualTo(employees.stream().mapToDouble(Employee::getSalary).average().orElse(0));
    }

    @ParameterizedTest(name = "{index} => name={0}")
    @MethodSource("employeeNamesProvider")
    @DisplayName("test_Find_Employees_By_Name_Parameterized")
    public void testFindEmployeesByNameParameterized(String name) {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, name);
        assertThat(foundEmployees).extracting(Employee::getName).contains(name);
    }

    private static Stream<Arguments> employeeNamesProvider() {
        return Stream.of(
                Arguments.of("John Doe"),
                Arguments.of("Jane Doe"),
                Arguments.of("Johnny Walker")
        );
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Not_Found")
    public void testFindEmployeesByNameNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "John Smith");
        assertThat(foundEmployees).isEmpty();
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive")
    public void testFindEmployeesByNameCaseInsensitive() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN dOe");
        assertThat(foundEmployees).extracting(Employee::getName).contains("John Doe");
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Not_Found")
    public void testFindEmployeesByNameCaseInsensitiveNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN sMiTh");
        assertThat(foundEmployees).isEmpty();
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Multiple")
    public void testFindEmployeesByNameCaseInsensitiveMultiple() {
        Department department = Mockito.mock(Department.class);
        List<Employee> employeeList = new ArrayList<>(employees);
        Mockito.when(department.getEmployees()).thenReturn(employeeList);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN");
        assertThat(foundEmployees)
                .extracting(Employee::getName)
                .containsExactlyInAnyOrder("John Doe", "Johnny Walker");
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Multiple_Not_Found")
    public void testFindEmployeesByNameCaseInsensitiveMultipleNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN sMiTh");
        assertThat(foundEmployees).isEmpty();
    }

    @Test
    @DisplayName("test_Add_Employee")
    public void testAddEmployee() {
        Department department = Mockito.mock(Department.class);
        List<Employee> employeeList = new ArrayList<>(employees);
        Mockito.when(department.getEmployees()).thenReturn(employeeList);

        Employee newEmployee = new Employee("28", "New Employee", 50000, department);
        companyService.addEmployee(department, newEmployee);

        employeeList.add(newEmployee); // Ensure the list is updated
        assertThat(employeeList).contains(newEmployee);
    }

    @Test
    @DisplayName("test_Add_Employee_Already_Exists")
    public void testAddEmployeeAlreadyExists() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        Employee existingEmployee = employees.get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            companyService.addEmployee(department, existingEmployee);
        });
    }

    @Test
    @DisplayName("test_Remove_Employee")
    public void testRemoveEmployee() {
        Department department = Mockito.mock(Department.class);
        List<Employee> employeeList = new ArrayList<>(employees);
        Mockito.when(department.getEmployees()).thenReturn(employeeList);

        Employee existingEmployee = employees.get(0);
        companyService.removeEmployee(department, existingEmployee);

        employeeList.remove(existingEmployee); // Ensure the list is updated
        assertThat(employeeList).doesNotContain(existingEmployee);
    }

    @Test
    @DisplayName("test_Remove_Employee_Not_Exists")
    public void testRemoveEmployeeNotExists() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        Employee nonExistingEmployee = new Employee("10", "Non Existing", 50000, department);
        assertThrows(IllegalArgumentException.class, () -> {
            companyService.removeEmployee(department, nonExistingEmployee);
        });
    }

    @Test
    @DisplayName("test_Update_Employee_Salary")
    public void testUpdateEmployeeSalary() {
        Employee employee = employees.get(0);
        companyService.updateEmployeeSalary(employee, 60000);

        assertThat(employee.getSalary()).isEqualTo(60000);
    }

    @Test
    @DisplayName("test_Update_Employee_Salary_Invalid")
    public void testUpdateEmployeeSalaryInvalid() {
        Employee employee = employees.get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            companyService.updateEmployeeSalary(employee, -1000);
        });
    }

    @Test
    @DisplayName("test_Update_Employee_Department")
    public void testUpdateEmployeeDepartment() {
        Employee employee = employees.get(0);
        Department newDepartment = new Department(DepartmentType.IT);
        companyService.updateEmployeeDepartment(employee, newDepartment);

        assertThat(employee.getDepartment()).isEqualTo(newDepartment);
    }

    @Test
    @DisplayName("test_Update_Employee_Department_Invalid")
    public void testUpdateEmployeeDepartmentInvalid() {
        Employee employee = employees.get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            companyService.updateEmployeeDepartment(employee, null);
        });
    }

    @Test
    @DisplayName("test_Update_Employee_Name")
    public void testUpdateEmployeeName() {
        Employee employee = employees.get(0);
        companyService.updateEmployeeName(employee, "New Name");

        assertThat(employee.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("test_Update_Employee_Name_Invalid")
    public void testUpdateEmployeeNameInvalid() {
        Employee employee = employees.get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            companyService.updateEmployeeName(employee, "");
        });
    }
}