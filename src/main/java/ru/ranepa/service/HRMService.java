package ru.ranepa.service;

import  ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HRMService {
    private final EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Расчет среднего значения зарплаты по всей компании
    public BigDecimal calculateAverageSalary() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = employees.stream()
                .map(Employee::getSalary)
                .filter(salary -> salary != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.divide(BigDecimal.valueOf(employees.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    // Поиск самого высокооплачиваемого сотрудника
    public Optional<Employee> findHighestPaidEmployee() {
        return repository.findAll().stream()
                .filter(emp -> emp.getSalary() != null)
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()));
    }

    // Фильтрация сотрудников по должности
    public List<Employee> findEmployeesByPosition(String position) {
        return repository.findAll().stream()
                .filter(emp -> position.equalsIgnoreCase(emp.getPosition()))
                .toList();
    }

    // Дополнительно: все методы репозитория (для удобства)
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return repository.findById(id);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }
}
