package org.ahmet;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {
    @Test
    public void testEmployeeCreation() {
        Employee employee = new Employee("1", "John Doe", 50000);
        assertThat(employee.getId()).isEqualTo("1");
        assertThat(employee.getName()).isEqualTo("John Doe");
        assertThat(employee.getSalary()).isEqualTo(50000);
    }
}