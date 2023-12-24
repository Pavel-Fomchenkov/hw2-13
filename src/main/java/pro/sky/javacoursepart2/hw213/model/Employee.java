package pro.sky.javacoursepart2.hw213.model;

import java.util.Objects;

public class Employee {
    private static int idCounter;
    private final int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private int departmentId;
    private double salary;


    public Employee(String firstName, String middleName, String lastName, int departmentId, double salary) {
        this.id = ++idCounter;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("id: %d - %s %s %s, отдел %d, оклад: %.2f",
                id, lastName, firstName, middleName, departmentId, salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(lastName, employee.lastName)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(middleName, employee.middleName)
                && departmentId == employee.departmentId
                && salary == employee.salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }
}
