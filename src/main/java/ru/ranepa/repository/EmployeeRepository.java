package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository {
    private final ArrayList<Employee> employees = new ArrayList<>();
    private final HashMap<Long, Employee> employeeById = new HashMap<>();
    private Long nextId = 1L;

    // Сохранение нового сотрудника (генерирует ID автоматически)
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId++);
        }

        // Удаляем старый, если уже был с таким ID
        if (employeeById.containsKey(employee.getId())) {
            employees.remove(employeeById.get(employee.getId()));
        }

        // Добавляем новый
        employees.add(employee);
        employeeById.put(employee.getId(), employee);

        return employee;
    }

    // Получение всех сотрудников
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    // Поиск по ID (возвращает Optional)
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employeeById.get(id));
    }

    // Удаление по ID
    public boolean delete(Long id) {
        Employee employee = employeeById.remove(id);
        if (employee != null) {
            return employees.remove(employee);
        }
        return false;
    }

    // Дополнительно: размер хранилища
    public int size() {
        return employees.size();
    }
}
