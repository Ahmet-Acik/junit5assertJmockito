package org.ahmet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyServiceTest {

    @Test
    @DisplayName("test_Calculate_Total_Salary")
    public void testCalculateTotalSalary() {
        Department department = Mockito.mock(Department.class);
        Employee employee1 = new Employee("1", "John Doe", 50000);
        Employee employee2 = new Employee("2", "Jane Doe", 60000);
        Mockito.when(department.getEmployees()).thenReturn(List.of(employee1, employee2));

        CompanyService companyService = new CompanyService();
        double totalSalary = companyService.calculateTotalSalary(department);
        assertThat(totalSalary).isEqualTo(110000);
    }

    @Test
    @DisplayName("test_Find_Employee_ById")
    public void testFindEmployeeById() {
        Department department = Mockito.mock(Department.class);
        Employee employee = new Employee("1", "John Doe", 50000);
        Mockito.when(department.getEmployees()).thenReturn(List.of(employee));

        CompanyService companyService = new CompanyService();
        Employee foundEmployee = companyService.findEmployeeById(department, "1");
        assertThat(foundEmployee).isEqualTo(employee);
    }

    @ParameterizedTest(name = "{index} => id={0}")
    @MethodSource("employeeIdsProvider")
    @DisplayName("test_Find_Employee_ById_Parameterized")
    public void testFindEmployeeByIdParameterized(String id) {
        Department department = Mockito.mock(Department.class);
        Employee employee = new Employee("1", "John Doe", 50000);
        Employee employee1 = new Employee("2", "Johny Doe", 60000);
        Employee employee2 = new Employee("3", "Johnathon Doe", 70000);
        Mockito.when(department.getEmployees()).thenReturn(List.of(employee, employee1, employee2));

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
        Employee employee = new Employee("1", "John Doe", 50000);
        Mockito.when(department.getEmployees()).thenReturn(List.of(employee));

        CompanyService companyService = new CompanyService();
        Employee foundEmployee = companyService.findEmployeeById(department, "2");
        assertThat(foundEmployee).isNull();
    }






}