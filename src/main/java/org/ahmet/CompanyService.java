package org.ahmet;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    public double calculateTotalSalary(Department department) {
        return department.getEmployees().stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Employee findEmployeeById(Department department, String id) {
        return department.getEmployees().stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployeesByDepartment(Department department) {
        return department.getEmployees();
    }

    public double calculateAverageSalary(Department department) {
        return department.getEmployees().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public List<Employee> findEmployeesByName(Department department, String name) {
        return department.getEmployees().stream()
                .filter(employee -> employee.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
}