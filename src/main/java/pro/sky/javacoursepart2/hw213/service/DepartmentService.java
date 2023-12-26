package pro.sky.javacoursepart2.hw213.service;

import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw213.model.Employee;

import java.util.List;
import java.util.Map;

@Service
public interface DepartmentService {

    List<Employee> find(int departmentId);

    double sumSalaryByDepartment(int departmentId);

    double maxSalaryByDepartment(int departmentId);

    double minSalaryByDepartment(int departmentId);

    Map<Integer, List<Employee>> groupByDepartment();
}
