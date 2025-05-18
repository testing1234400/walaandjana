package all;

import java.util.ArrayList;
import java.util.List;

public class chef extends Person {

    private String expertise;
    private List<String> assignedTasks = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    // Constructor
    public chef(String userName, String expertise, String pass, String role) {
        super(userName, pass, role);
        this.expertise = expertise;
    }

    // Getter for expertise
    public String getExpertise() {
        return expertise;
    }

    // Getter for assigned tasks
    public List<String> getAssignedTasks() {
        return assignedTasks;
    }

    // Assign task method
    public void assignTask(String task) {
        assignedTasks.add(task);
        String message = "Task assigned: " + task;
        notifications.add(message);
        System.out.println("🔔 " + message + " to " + getUserName());
    }
    public void assignTask1(String task) {
        assignedTasks.add(task);
        String message = "Task assigned: " + task;
        notifications.add(message);
        //System.out.println("🔔 " + message + " to " + getUserName());
    }

    // Get task count
    public int getTaskCount() {
        return assignedTasks.size();
    }

    // Validation method
    public boolean isValid() {
        return getUserName() != null && expertise != null;
    }

    // Getter for notifications
    public List<String> getNotifications() {
        return notifications;
    }
}
