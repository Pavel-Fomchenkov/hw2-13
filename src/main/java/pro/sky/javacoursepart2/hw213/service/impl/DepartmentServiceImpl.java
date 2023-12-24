package pro.sky.javacoursepart2.hw213.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw213.model.Employee;
import pro.sky.javacoursepart2.hw213.service.DepartmentService;
import pro.sky.javacoursepart2.hw213.service.EmployeeService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public List<Employee> find(int departmentId) {
        return employeeService.readEmployeeStorage().values().stream()
                .filter(v -> v.getDepartmentId() == departmentId)
                .collect(Collectors.toList());
    }

    @Override
    public double sumSalaryByDepartment(int departmentId) {
        return employeeService.readEmployeeStorage().values().stream()
                .filter(v -> v.getDepartmentId() == departmentId)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    @Override
    public double maxSalaryByDepartment(int departmentId) {
        return employeeService.readEmployeeStorage().values().stream()
                .filter(v -> v.getDepartmentId() == departmentId)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElse(0.0);
    }

    @Override
    public double minSalaryByDepartment(int departmentId) {
        return employeeService.readEmployeeStorage().values().stream()
                .filter(v -> v.getDepartmentId() == departmentId)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElse(0.0);
    }

    @Override
    public Map<Integer, List<Employee>> groupByDepartment() {
        return employeeService.readEmployeeStorage().values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId));
    }
}
