package pro.sky.javacoursepart2.hw213.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.javacoursepart2.hw213.model.Employee;
import pro.sky.javacoursepart2.hw213.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("{departmentId}/employee")
    public List<Employee> findEmployeesByDepartment(@PathVariable("departmentId") int departmentId) {
        return departmentService.find(departmentId);
    }

    @GetMapping("{departmentId}/salary/sum")
    public double sumSalaryByDepartment(@PathVariable("departmentId") int departmentId) {
        return departmentService.sumSalaryByDepartment(departmentId);
    }

    @GetMapping("{departmentId}/salary/max")
    public double maxSalaryByDepartment(@PathVariable("departmentId") int departmentId) {
        return departmentService.maxSalaryByDepartment(departmentId);
    }

    @GetMapping("{departmentId}/salary/min")
    public double minSalaryByDepartment(@PathVariable("departmentId") int departmentId) {
        return departmentService.minSalaryByDepartment(departmentId);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> groupByDepartment() {
        return departmentService.groupByDepartment();
    }
}
