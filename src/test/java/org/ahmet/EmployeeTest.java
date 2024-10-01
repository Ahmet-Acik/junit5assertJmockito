package org.ahmet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EmployeeTest {
    @Test
    public void testEmployeeCreation() {
        Employee employee = new Employee("1", "John Doe", 50000, new Department(DepartmentType.HR));
        assertAll(
                () -> assertThat(employee.getId()).isEqualTo("1"),
                () -> assertThat(employee.getName()).isEqualTo("John Doe"),
                () -> assertThat(employee.getDepartment().getType()).isEqualTo(DepartmentType.HR),
                () -> assertThat(employee.getSalary()).isEqualTo(50000)

        );

    }
}