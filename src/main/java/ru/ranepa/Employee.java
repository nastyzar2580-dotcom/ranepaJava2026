package ru.ranepa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private static long nextId = 1L;
    private final long id;
    private String name;
    private String position;        // Должность
    private BigDecimal salary;      // Заработная плата
    private final LocalDate hireDate; // Дата приема на работу (не изменяется)

    /**
     * Конструктор Employee с автоматическим присвоением ID.
     */
    public Employee(String name, String position, BigDecimal salary, LocalDate hireDate) {
        this.id = nextId++;         // Присваиваем уникальное значение ID
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Геттеры (геттеры используются для чтения значений полей)
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    // Сеттеры (методы для изменения значений полей)
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }
}
