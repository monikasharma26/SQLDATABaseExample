package com.example.sqldatabaseexample.model;

public class Employee {

    int id;
    String name,department,joingDate;
    double salary;

    public Employee(int id, String name, String department, String joingDate, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.joingDate = joingDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJoingDate() {
        return joingDate;
    }

    public double getSalary() {
        return salary;
    }
}
