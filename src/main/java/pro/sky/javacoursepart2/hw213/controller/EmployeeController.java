package pro.sky.javacoursepart2.hw213.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.javacoursepart2.hw213.model.Employee;
import pro.sky.javacoursepart2.hw213.service.EmployeeService;

import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public Map<String, Employee> readEmployeeStorage() {
        return employeeService.readEmployeeStorage();
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam String firstName,
                                @RequestParam String middleName,
                                @RequestParam String lastName,
                                @RequestParam int departmentId,
                                @RequestParam double salary) {
        return employeeService.addEmployee(firstName, middleName, lastName, departmentId, salary);
    }

    @GetMapping(value = "/find", params = "id")
    public Employee findEmployee(@RequestParam int id) {
        return employeeService.findEmployee(id);
    }

    @GetMapping(value = "/edit", params = {"id", "departmentId", "salary"})
    public Employee editEmployee(@RequestParam("id") int id,
                                 @RequestParam("departmentId") int departmentId,
                                 @RequestParam("salary") double salary) {
        return employeeService.editEmployee(id, departmentId, salary);
    }

    @GetMapping(value = "remove", params = {"id", "firstName", "middleName", "lastName"})
    public Employee removeEmployee(@RequestParam("id") int id,
                                   @RequestParam("firstName") String firstName,
                                   @RequestParam("middleName") String middleName,
                                   @RequestParam("lastName") String lastName) {
        return employeeService.removeEmployee(id, firstName, middleName, lastName);
    }

    // CODE FOR AUTOMATING ROUTINE OPERATIONS WHILE TESTING
    @GetMapping("/test")
    public Map<String, Employee> testEmployee() {
        addEmployee("Ivan", "Ivanovich", "Ivanov", 1, 100.0);
        addEmployee("Petr", "Petrovich", "Petrov", 2, 75.0);
        addEmployee("Sidor", "Sidorovich", "Sidorov", 3, 50.0);
        addEmployee("Aleksey", "Alekseevich", "Alekseev", 1, 80.0);
        addEmployee("Vasiliy", "Vasilievich", "Vasilyev", 2, 60.0);
        addEmployee("Pavel", "Pavlovich", "Pavlov", 3, 100.0);
        addEmployee("Andrey", "Andreevich", "Andreev", 1, 100.0);
        addEmployee("Aleksandr", "Aleksandrovich", "Aleksandrov", 2, 100.0);
        addEmployee("Dmitriy", "Dmitrievich", "Dmitriev", 3, 90.0);
        addEmployee("Nikolay", "Nikolaevich", "Nikolaev", 1, 85.5);
        return readEmployeeStorage();
    }
    // END OF CODE FOR AUTOMATING ROUTINE OPERATIONS WHILE TESTING
}
