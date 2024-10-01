package org.ahmet;

import org.junit.jupiter.api.Test;

import static org.ahmet.DepartmentType.HR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DepartmentTest {
    @Test
    public void testAddEmployee() {
        Department department = new Department(HR);
        Employee employee = new Employee("1", "John Doe", 50000, department);
        department.addEmployee(employee);

        assertAll(
                // Assert that the employee is added to the department
                () -> assertThat(department.getEmployees()).contains(employee),
                // Additional assertions to ensure the integrity of the objects
                () -> assertThat(employee.getDepartment()).isEqualTo(department),
                () -> assertThat(department.getEmployees()).hasSize(1),
                () -> assertThat(employee.getName()).isEqualTo("John Doe"),
                () -> assertThat(employee.getSalary()).isEqualTo(50000),
                () -> assertThat(employee.getId()).isEqualTo("1"),
                () -> assertThat(department.getType()).isEqualTo(HR)
        );
    }

    @Test
    public void testRemoveEmployee() {
        Department department = new Department(HR);
        Employee employee = new Employee("1", "John Doe", 50000, department);
        department.addEmployee(employee);
        department.removeEmployee(employee);
        assertThat(department.getEmployees()).doesNotContain(employee);
    }


}