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
                .filter(employee -> employee.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void addEmployee(Department department, Employee employee) {
        if (!department.getEmployees().contains(employee)) {
            department.addEmployee(employee);
        } else {
            throw new IllegalArgumentException("Employee already exists in the department.");
        }
    }

    public void removeEmployee(Department department, Employee employee) {
        if (department.getEmployees().contains(employee)) {
            department.removeEmployee(employee);
        } else {
            throw new IllegalArgumentException("Employee does not exist in the department.");
        }
    }

    public void updateEmployeeSalary(Employee employee, double salary) {
        if (salary > 0) {
            employee.setSalary(salary);
        } else {
            throw new IllegalArgumentException("Salary must be greater than zero.");
        }
    }

    public void updateEmployeeDepartment(Employee employee, Department department) {
        if (department != null) {
            employee.setDepartment(department);
        } else {
            throw new IllegalArgumentException("Department cannot be null.");
        }
    }

    public void updateEmployeeName(Employee employee, String name) {
        if (name != null && !name.trim().isEmpty()) {
            employee.setName(name);
        } else {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

}