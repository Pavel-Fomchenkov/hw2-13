package pro.sky.javacoursepart2.hw213.service;

import pro.sky.javacoursepart2.hw213.model.Employee;

import java.util.Map;

public interface EmployeeService {
    Employee addEmployee(String firstName, String middleName, String lastName, int departmentId, double salary);

    Map<String, Employee> readEmployeeStorage();

    Employee findEmployee(int id);

    Employee editEmployee(int id, int departmentId, double salary);

    Employee removeEmployee(int id, String firstName, String middleName, String lastName);


}
