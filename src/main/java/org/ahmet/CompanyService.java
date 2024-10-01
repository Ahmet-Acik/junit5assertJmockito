package org.ahmet;

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
}