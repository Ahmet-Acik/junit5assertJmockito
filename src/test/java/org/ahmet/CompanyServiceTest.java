package org.ahmet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.ahmet.DepartmentType.HR;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyServiceTest {
    private List<Employee> employees;

    @BeforeEach
    public void setUp() {
        Department hr = new Department(HR);
        Department it = new Department(DepartmentType.IT);
        Department sales = new Department(DepartmentType.SALES);
        Department finance = new Department(DepartmentType.FINANCE);
        Department marketing = new Department(DepartmentType.MARKETING);

        employees = Stream.of(
                new Employee("1", "John Doe", 50000, hr),
                new Employee("2", "Jane Doe", 60000, hr),
                new Employee("3", "Jim Beam", 55000, hr),
                new Employee("4", "Jack Daniels", 70000, it),
                new Employee("5", "Johnny Walker", 80000, it),
                new Employee("6", "Jameson Irish", 75000, it),
                new Employee("7", "Jose Cuervo", 65000, sales),
                new Employee("8", "Jagermeister", 62000, sales),
                new Employee("9", "Captain Morgan", 67000, sales),
                new Employee("10", "Bacardi", 68000, hr),
                new Employee("11", "Smirnoff", 69000, it),
                new Employee("12", "Grey Goose", 71000, sales),
                new Employee("13", "Ciroc", 72000, hr),
                new Employee("14", "Belvedere", 73000, it),
                new Employee("15", "Ketel One", 74000, sales),
                new Employee("16", "Absolut", 76000, hr),
                new Employee("17", "Skyy", 77000, it),
                new Employee("18", "Stolichnaya", 78000, sales),
                new Employee("19", "Svedka", 79000, hr),
                new Employee("20", "Tito's", 80000, it),
                new Employee("21", "Deep Eddy", 81000, finance),
                new Employee("22", "New Amsterdam", 82000, marketing),
                new Employee("23", "Three Olives", 83000, finance),
                new Employee("24", "Pinnacle", 84000, marketing),
                new Employee("25", "Finlandia", 85000, finance),
                new Employee("26", "Crystal Head", 86000, marketing),
                new Employee("27", "Reyka", 87000, finance)
        ).collect(Collectors.toList());
    }

    @Test
    @DisplayName("test_Calculate_Total_Salary")
    public void testCalculateTotalSalary() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        double totalSalary = companyService.calculateTotalSalary(department);
        assertThat(totalSalary).isEqualTo(employees.stream().mapToDouble(Employee::getSalary).sum());
    }

    @Test
    @DisplayName("test_Find_Employee_ById")
    public void testFindEmployeeById() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        Employee foundEmployee = companyService.findEmployeeById(department, "1");
        assertThat(foundEmployee).isEqualTo(employees.get(0));
    }

    @ParameterizedTest(name = "{index} => id={0}")
    @MethodSource("employeeIdsProvider")
    @DisplayName("test_Find_Employee_ById_Parameterized")
    public void testFindEmployeeByIdParameterized(String id) {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        Employee foundEmployee = companyService.findEmployeeById(department, id);
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

        CompanyService companyService = new CompanyService();
        Employee foundEmployee = companyService.findEmployeeById(department, "28");
        assertThat(foundEmployee).isNull();
    }

    @Test
    @DisplayName("find_Employees_ByDepartment")
    public void findEmployeesByDepartment() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> employeesByDept = companyService.findEmployeesByDepartment(department);
        assertThat(employeesByDept).containsAll(employees);
    }

    @Test
    @DisplayName("test_Calculate_Average_Salary")
    public void testCalculateAverageSalary() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        double averageSalary = companyService.calculateAverageSalary(department);
        assertThat(averageSalary).isEqualTo(employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0));
    }

    @ParameterizedTest(name = "{index} => name={0}")
    @MethodSource("employeeNamesProvider")
    @DisplayName("test_Find_Employees_By_Name_Parameterized")
    public void testFindEmployeesByNameParameterized(String name) {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, name);
        assertThat(foundEmployees).extracting(Employee::getName).contains(name);
    }

    private static Stream<Arguments> employeeNamesProvider() {
        return Stream.of(
                Arguments.of("John Doe"),
                Arguments.of("Jane Doe"),
                Arguments.of("Jim Beam"),
                Arguments.of("Jack Daniels"),
                Arguments.of("Johnny Walker"),
                Arguments.of("Jameson Irish"),
                Arguments.of("Jose Cuervo")
        );
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Not_Found")
    public void testFindEmployeesByNameNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "John Smith");
        assertThat(foundEmployees).isEmpty();
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive")
    public void testFindEmployeesByNameCaseInsensitive() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN dOe");
        assertThat(foundEmployees).extracting(Employee::getName).contains("John Doe");
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Not_Found")
    public void testFindEmployeesByNameCaseInsensitiveNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN sMiTh");
        assertThat(foundEmployees).isEmpty();
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Multiple")
    public void testFindEmployeesByNameCaseInsensitiveMultiple() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN");
        assertThat(foundEmployees).extracting(Employee::getName).contains("John Doe", "Johnny Walker");
    }

    @Test
    @DisplayName("test_Find_Employees_By_Name_Case_Insensitive_Multiple_Not_Found")
    public void testFindEmployeesByNameCaseInsensitiveMultipleNotFound() {
        Department department = Mockito.mock(Department.class);
        Mockito.when(department.getEmployees()).thenReturn(employees);

        CompanyService companyService = new CompanyService();
        List<Employee> foundEmployees = companyService.findEmployeesByName(department, "jOhN sMiTh");
        assertThat(foundEmployees).isEmpty();
    }



}