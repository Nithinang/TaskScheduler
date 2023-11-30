package Manage;
import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private LocalDate dueDate;
    private Priority priority;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public Task(String name, String description, LocalDate dueDate, Priority priority) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task: " + name + "\nDescription: " + description +
                "\nDue Date: " + dueDate + "\nPriority: " + priority + "\n";
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getName(){
        return name;
    }
}