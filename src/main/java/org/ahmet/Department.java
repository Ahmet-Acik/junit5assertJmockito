package org.ahmet;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final DepartmentType type;
    private final List<Employee> employees;

 public Department(DepartmentType type) {
        this.type = type;
        this.employees = new ArrayList<>();
    }

  public DepartmentType getType() {
        return type;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
}