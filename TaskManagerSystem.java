import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import Manage.Task;
import Manage.TaskManager;

public class TaskManagerSystem {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Schedule a timer task to check for overdue tasks every 5 minutes
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer task executed");
                checkOverdueTasks();
            }
        }, 0, 5 * 60 * 1000);

        while (true) {
            System.out.println("Task Scheduler Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Trigger Notifications");
            System.out.println("4. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    checkOverdueTasks();
                    break;
                case 4:
                    timer.cancel(); // Stop the timer when exiting
                    System.out.println("Exiting the Task Scheduler. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void checkOverdueTasks() {
        List<Task> overdueTasks = taskManager.getOverdueTasks();
    
        if (overdueTasks.isEmpty()) {
            System.out.println("No overdue tasks found.");
            return;
        }
    
        System.out.println("Overdue Tasks:\n");
    
        for (Task task : overdueTasks) {
            System.out.println(task);
        }
    
        System.out.print("Do you want to mark previously overdue tasks as completed? (yes/no): ");
        String response = scanner.next().toLowerCase();
    
        if (response.equals("yes")) {
            markOverdueTasksAsCompleted(overdueTasks);
        }
    }
    
    private static void markOverdueTasksAsCompleted(List<Task> overdueTasks) {
        for (Task task : overdueTasks) {
            System.out.println("Marking task '" + task.getName() + "' as completed.");
            // Add logic here to mark the task as completed in your TaskManager or update its status.
        }
    
        System.out.println("Previously overdue tasks marked as completed.\n");
    }    

    private static int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    private static void addTask() {
        System.out.println("Enter task details:");
        System.out.print("Task Name: ");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        // If there is an overdue task, prevent adding another task until it's completed
        if (taskManager.getLastDueDate() != null && taskManager.getLastDueDate().isBefore(LocalDate.now())) {
            System.out.println("You have an overdue task. Please complete it before adding another task.");
            return;
        }

        LocalDate dueDate;
        try {
            System.out.print("Due Date (YYYY-MM-DD): ");
            dueDate = LocalDate.parse(scanner.next());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please use the format YYYY-MM-DD.");
            return; // exit the method
        }

        System.out.print("Priority (HIGH, MEDIUM, LOW): ");
        Task.Priority priority = Task.Priority.valueOf(scanner.next().toUpperCase());

        Task newTask = new Task(name, description, dueDate, priority);
        taskManager.addTask(newTask);

        System.out.println("Task added successfully!\n");
    }

    private static void viewTasks() {
        List<Task> tasks = taskManager.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.\n");
        } else {
            System.out.println("Tasks:\n");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }
}
