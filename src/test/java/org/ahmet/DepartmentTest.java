package org.ahmet;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DepartmentTest {
    @Test
    public void testAddEmployee() {
        Department department = new Department("HR");
        Employee employee = new Employee("1", "John Doe", 50000);
        department.addEmployee(employee);
        assertThat(department.getEmployees()).contains(employee);
    }

    @Test
    public void testRemoveEmployee() {
        Department department = new Department("HR");
        Employee employee = new Employee("1", "John Doe", 50000);
        department.addEmployee(employee);
        department.removeEmployee(employee);
        assertThat(department.getEmployees()).doesNotContain(employee);
    }
}