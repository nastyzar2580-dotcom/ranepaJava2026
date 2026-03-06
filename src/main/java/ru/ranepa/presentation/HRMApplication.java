package ru.ranepa.presentation;



import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class HRMApplication {
    private final HRMService service;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public HRMApplication() {
        EmployeeRepository repository = new EmployeeRepository();
        this.service = new HRMService(repository);
    }

    public void start() {
        int choice;
        while (true) {
            showMenu();
            choice = getIntInput("choose an option: ");

            switch (choice) {
                case 1 -> showAllEmployees();
                case 2 -> addEmployee();
                case 3 -> deleteEmployee();
                case 4 -> findEmployeeById();
                case 5 -> showStatistics();
                case 0 -> {
                    System.out.println("GoodBie!");
                    return;
                }
                default -> System.out.println("incoorect choose! try again.");
            }
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }

    private void showMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Find employee ID");
        System.out.println("5. Show statistics");
        System.out.println("0. Exit");
    }

    private void showAllEmployees() {
        List<Employee> employees = service.findAll();
        if (employees.isEmpty()) {
            System.out.println("Employee do not found.");
            return;
        }
        System.out.println("\nList of employee:");
        employees.forEach(System.out::println);
    }

    private void addEmployee() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Job title: ");
        String position = scanner.nextLine();

        System.out.print("Salary: ");
        BigDecimal salary = new BigDecimal(scanner.nextLine());

        LocalDate hireDate;
        while (true) {
            try {
                System.out.print("Date (dd.MM.yyyy): ");
                String dateStr = scanner.nextLine();
                hireDate = LocalDate.parse(dateStr, dateFormatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Incorrect date! Use dd.MM.yyyy");
            }
        }

        Employee employee = new Employee(name, position, salary, hireDate);
        service.save(employee);
        System.out.println("Employee added! ID: " + employee.getId());
    }

    private void deleteEmployee() {
        Long id = getLongInput("ID employee for delete: ");
        if (service.delete(id)) {
            System.out.println("employee was deleted.");
        } else {
            System.out.println("employee ID " + id + " dont found.");
        }
    }

    private void findEmployeeById() {
        Long id = getLongInput("ID employee: ");
        service.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Employee ID " + id + " dont found.")
        );
    }

    private void showStatistics() {
        BigDecimal avgSalary = service.calculateAverageSalary();
        System.out.println("Average salary: " + avgSalary);

        service.findHighestPaidEmployee().ifPresent(emp ->
                System.out.println("Top-manager: " + emp.getName() + " (" + emp.getSalary() + ")")
        );
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("input number!");
            }
        }
    }

    private Long getLongInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input number!");
            }
        }
    }

    public static void main(String[] args) {
        new HRMApplication().start();
    }
}
