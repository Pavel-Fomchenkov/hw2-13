package pro.sky.javacoursepart2.hw213.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw213.model.Employee;
import pro.sky.javacoursepart2.hw213.service.EmployeeService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employeeStorage = new HashMap<>();

    private final int MAX_EMPLOYEES = 10;

    public int getMAX_EMPLOYEES() {
        return MAX_EMPLOYEES;
    }

    @Override
    public Map<String, Employee> readEmployeeStorage() {
        return Collections.unmodifiableMap(employeeStorage);
    }

    @Override
    public Employee addEmployee(String firstName, String middleName, String lastName, int departmentId, double salary) {
        firstName = convertName(firstName);
        middleName = convertName(middleName);
        lastName = convertName(lastName);
        String key = generateEmployeeStorageKey(firstName, middleName, lastName);
        if (employeeStorage.containsKey(key)) {
            throw new RuntimeException("в БД уже есть сотрудник с таким именем, нельзя дублировать сотрудников");
        } else {
            if (employeeStorage.size() == MAX_EMPLOYEES) {
                throw new RuntimeException("База данных переполнена!");
            }
            Employee employee = new Employee(firstName, middleName, lastName, departmentId, salary);
            employeeStorage.put(key, employee);
            return employee;
        }
    }

    @Override
    public Employee findEmployee(int id) {
        return employeeStorage.values().stream()
                .filter(v -> v.getId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Cотрудник с id " + id + " отсутвует в базе данных."));
    }

    @Override
    public Employee editEmployee(int id, int departmentId, double salary) {
        Employee editable = findEmployee(id);
        editable.setDepartmentId(departmentId);
        editable.setSalary(salary);
        return editable;
    }

    @Override
    public Employee removeEmployee(int id, String firstName, String middleName, String lastName) {
        Employee editable = findEmployee(id);
        if (editable.getFirstName().equals(firstName)
                && editable.getMiddleName().equals(middleName)
                && editable.getLastName().equals(lastName)) {
            return employeeStorage.remove(generateEmployeeStorageKey(firstName, middleName, lastName));
        } else {
            throw new IllegalArgumentException("Для избежания случайного удаления сотрудника необходимо ввести корректные значения Id и ФИО");
        }
    }

    public String generateEmployeeStorageKey(String firstName, String middleName, String lastName) {
        return firstName + middleName + lastName;
    }

    private String convertName(String name) {
        if (StringUtils.isAlpha(name)) {
            return StringUtils.capitalize(name.toLowerCase());
        } else {
            throw new RuntimeException(name + " - не допустимое значение имени, отчества или фамилии");
        }
    }

}
