package Manage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private LocalDate lastDueDate = null;

    public void addTask(Task task) {
        tasks.add(task);
        lastDueDate = task.getDueDate();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasksDueToday() {
        List<Task> dueToday = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Task task : tasks) {
            if (task.getDueDate().isEqual(today)) {
                dueToday.add(task);
            }
        }

        return dueToday;
    }

    public List<Task> getOverdueTasks() {
        List<Task> overdueTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.isOverdue()) {
                overdueTasks.add(task);
            }
        }

        return overdueTasks;
    }

    public LocalDate getLastDueDate() {
        return lastDueDate;
    }
}