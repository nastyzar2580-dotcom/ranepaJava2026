

package service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class HRMServiceTest {

    private HRMService service;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
        service = new HRMService(repository);
    }

    @Test
    void shouldCalculateAverageSalary() {
        // Given - подготовка тестовых данных
        service.save(new Employee("Иван", "Developer", new BigDecimal("100000"), LocalDate.now()));
        service.save(new Employee("Петр", "Manager", new BigDecimal("200000"), LocalDate.now()));
        service.save(new Employee("Мария", "QA", new BigDecimal("300000"), LocalDate.now()));

        // When - действие
        BigDecimal average = service.calculateAverageSalary();

        // Then - проверка
        assertEquals(new BigDecimal("200000"), average);
    }

    @Test
    void shouldFindHighestPaidEmployee() {
        // Given
        service.save(new Employee("Иван", "Developer", new BigDecimal("100000"), LocalDate.now()));
        service.save(new Employee("Петр", "Manager", new BigDecimal("250000"), LocalDate.now()));
        service.save(new Employee("Мария", "QA", new BigDecimal("150000"), LocalDate.now()));

        // When
        var topEmployee = service.findHighestPaidEmployee();

        // Then
        assertTrue(topEmployee.isPresent());
        assertEquals("Петр", topEmployee.get().getName());
        assertEquals(new BigDecimal("250000"), topEmployee.get().getSalary());
    }

    @Test
    void shouldReturnEmptyForEmptyRepository() {
        // When
        BigDecimal average = service.calculateAverageSalary();
        var topEmployee = service.findHighestPaidEmployee();

        // Then
        assertEquals(BigDecimal.ZERO, average);
        assertFalse(topEmployee.isPresent());
    }

    @Test
    void shouldFilterEmployeesByPosition() {
        // Given
        service.save(new Employee("Иван", "Developer", new BigDecimal("100000"), LocalDate.now()));
        service.save(new Employee("Петр", "Manager", new BigDecimal("200000"), LocalDate.now()));
        service.save(new Employee("Анна", "Developer", new BigDecimal("120000"), LocalDate.now()));

        // When
        var developers = service.findEmployeesByPosition("Developer");

        // Then
        assertEquals(2, developers.size());
        assertTrue(developers.stream().allMatch(emp -> "Developer".equals(emp.getPosition())));
    }
}
