package pro.sky.javacoursepart2.hw213.service.impl;

import org.junit.jupiter.api.Test;
import pro.sky.javacoursepart2.hw213.model.Employee;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {
    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    @Test
    void testingAddEmployee() {
        employeeService.addEmployee("Ivan", "Ivanovich", "Ivanov", 1, 100.0);
        employeeService.addEmployee("Petr", "Petrovich", "Petrov", 2, 75.0);
        employeeService.addEmployee("Sidor", "Sidorovich", "Sidorov", 3, 50.0);
        employeeService.addEmployee("Aleksey", "Alekseevich", "Alekseev", 1, 80.0);
        employeeService.addEmployee("Vasiliy", "Vasilievich", "Vasilyev", 2, 60.0);
        employeeService.addEmployee("Pavel", "Pavlovich", "Pavlov", 3, 100.0);
        Employee actual = employeeService.addEmployee("Nikolay", "Nikolaevich", "Nikolaev", 1, 85.5);
        Employee expected = new Employee("Nikolay", "Nikolaevich", "Nikolaev", 1, 85.5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmployeeWhenAddNew() {
        Employee expected = new Employee("Dmitriy", "Dmitrievich", "Dmitriev", 3, 90.0);
        assertEquals(expected, employeeService.addEmployee("Dmitriy", "Dmitrievich", "Dmitriev", 3, 90.0));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenTryingToAddEmployeeThatAlreadyExist() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.addEmployee("Pavel", "Pavlovich", "Pavlov", 3, 100.0);
            employeeService.addEmployee("Pavel", "Pavlovich", "Pavlov", 3, 100.0);
        });
        assertEquals("в БД уже есть сотрудник с таким именем, нельзя дублировать сотрудников", exception.getMessage());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenEmployeeStorageIsOverloaded() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i <= employeeService.getMAX_EMPLOYEES(); i++) {
                String addition = String.valueOf(i).chars()
                        .map(ch -> ch + 17)
                        .mapToObj(c -> String.valueOf((char) c))
                        .reduce((x, y) -> new StringBuffer(x).append(y).toString())
                        .get();
                employeeService.addEmployee("FirstName" + addition, "MiddleName", "LastName", 1, 10.1);
            }
        });
        assertEquals("База данных переполнена!", exception.getMessage());
    }

    @Test
    void readEmployeeStorageShouldReturnCorrectMap() {
        Map<String, Employee> expected = new HashMap<>() {{
            put("FullNameOne", new Employee("Full", "Name", "One", 1, 10.1));
            put("FakeNameTwo", new Employee("Fake", "Name", "Two", 1, 10.2));
            put("RealNickThree", new Employee("Real", "Nick", "Three", 2, 20.3));
        }};

        employeeService.addEmployee("Full", "Name", "One", 1, 10.1);
        employeeService.addEmployee("Fake", "Name", "Two", 1, 10.2);
        employeeService.addEmployee("Real", "Nick", "Three", 2, 20.3);
        assertEquals(expected, employeeService.readEmployeeStorage());
    }

    @Test
    void findEmployeeShouldReturnCorrectEmployee() {
        employeeService.addEmployee("Fake", "Name", "Employee", 1, 10.2);
        employeeService.addEmployee("Real", "Name", "Employee", 2, 20.3);
        Employee actual = employeeService.findEmployee(1);
        Employee expected = employeeService.readEmployeeStorage().values().stream()
                .filter(v -> v.getId() == 1)
                .findAny()
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldCorrectlyEditEmployee() {
        employeeService.addEmployee("Full", "Name", "One", 1, 10.1);
        employeeService.addEmployee("Георгий", "Георгиевич", "Георгиев", 1, 10.2);
        int idToFind = employeeService.readEmployeeStorage().values().stream()
                .map(Employee::getId)
                .findAny()
                .get();
        Employee actual = employeeService.editEmployee(idToFind, 2, 0.199);
        Employee expected = employeeService.findEmployee(idToFind);
        assertTrue(expected.equals(actual) && expected.getDepartmentId() == 2 && expected.getSalary() == 0.199);
    }

    @Test
    void removeEmployee() {
        employeeService.addEmployee("Георгий", "Георгиевич", "Георгиев", 1, 10.2);
        employeeService.addEmployee("Aleksey", "Alekseevich", "Alekseev", 1, 80.0);
        employeeService.addEmployee("Vasiliy", "Vasilievich", "Vasilyev", 2, 60.0);
        int idToFind = employeeService.readEmployeeStorage().values().stream()
                .map(Employee::getId)
                .findAny()
                .get();
        Employee expected = employeeService.readEmployeeStorage().values().stream()
                .filter((v -> v.getId() == idToFind))
                .findAny()
                .orElse(null);
        Employee actual = employeeService.removeEmployee(idToFind, expected.getFirstName(), expected.getMiddleName(), expected.getLastName());
        assertEquals(expected, actual);
        Employee deleted = employeeService.readEmployeeStorage().values().stream()
                .filter((v -> v.getId() == idToFind))
                .findAny()
                .orElse(null);
        assertNull(deleted);
    }
    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemovingWithWrongArguments() {
        employeeService.addEmployee("Vasiliy", "Vasilievich", "Vasilyev", 2, 60.0);
        assertThrows(IllegalArgumentException.class, () ->
        employeeService.removeEmployee(1, "Абвгде", "Abcde", "Ъыьэюя"));
    }
    @Test
    void generateEmployeeStorageKey() {
        String firstName = "мЕдвеДь";
        String middleName = "потаПыч";
        String lastName = "косолаПЫЙ";
        String expected = "МедведьПотапычКосолапый";
        assertEquals(firstName + middleName + lastName,
                employeeService.generateEmployeeStorageKey(firstName, middleName, lastName));
    }
}