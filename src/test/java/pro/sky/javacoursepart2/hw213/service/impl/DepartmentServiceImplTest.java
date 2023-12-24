package pro.sky.javacoursepart2.hw213.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.javacoursepart2.hw213.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private EmployeeServiceImpl employeeService;
    @InjectMocks
    private final DepartmentServiceImpl departmentService = new DepartmentServiceImpl(employeeService);

    public DepartmentServiceImplTest(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    private final List<Employee> employees = new ArrayList<>(List.of(
            new Employee("Firstnamea", "Middlename", "Lastname", 1, 1.01),
            new Employee("FirstNameb", "Middlename", "lastName", 1, 1.02),

            new Employee("Firstnamec", "Middlename", "Lastname", 2, 2.01),
            new Employee("Firstnamed", "Middlename", "Lastname", 2, 2.02),
            new Employee("Firstnamee", "Middlename", "Lastname", 2, 2.03),

            new Employee("Firstnamef", "Middlename", "Lastname", 3, 3.01))
    );

    private Map<String, Employee> employeesMapSource() {
        Map<String, Employee> employeeStorage = new HashMap<>();
        for (Employee employee : employees) {
            employeeStorage.put(employee.getFirstName() + employee.getMiddleName() + employee.getLastName(), employee);
        }
        return employeeStorage;
    }

    @Test
    void shouldFindAllEmployeesFromGivenDepartment() {
        when(employeeService.readEmployeeStorage()).thenReturn(employeesMapSource());
        int departmentId = 2;
        List<Employee> actual = departmentService.find(departmentId);
        List<Employee> expected = new ArrayList<>(List.of(employees.get(2), employees.get(3), employees.get(4)));
        assertEquals(expected, actual);
    }

    @Test
    void shouldSumSalaryByDepartment() {
        when(employeeService.readEmployeeStorage()).thenReturn(employeesMapSource());
        int departmentId = 1;
        double expected = 2.03;
        assertEquals(expected, departmentService.sumSalaryByDepartment(departmentId));
    }

    @Test
    void findMaxSalaryByDepartment() {
        when(employeeService.readEmployeeStorage()).thenReturn(employeesMapSource());
        int departmentId = 2;
        double expected = 2.03;
        assertEquals(expected, departmentService.maxSalaryByDepartment(departmentId));
    }

    @Test
    void findMinSalaryByDepartment() {
        when(employeeService.readEmployeeStorage()).thenReturn(employeesMapSource());
        int departmentId = 2;
        double expected = 2.01;
        assertEquals(expected, departmentService.minSalaryByDepartment(departmentId));
    }

    @Test
    void shouldReturnMapGroupedByDepartment() {
        when(employeeService.readEmployeeStorage()).thenReturn(employeesMapSource());
        Map<Integer, List<Employee>> expected = new HashMap<>(){{
            put(1, List.of(employees.get(0), employees.get(1)));
            put(2, List.of(employees.get(2), employees.get(3), employees.get(4)));
            put(3, List.of(employees.get(5)));
        }};
        assertEquals(expected, departmentService.groupByDepartment());
    }
}