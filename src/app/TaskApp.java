package src.app;

import src.model.Task;
import src.service.TaskService;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskService service = new TaskService();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Count Tasks");
            System.out.println("4. Save Tasks");
            System.out.println("5. Load Tasks");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter task name: ");
                        service.addTask(sc.nextLine());
                        System.out.println("Task added successfully");
                        break;

                    case 2:
                        if (service.getTaskCount() == 0) {
                            System.out.println("No tasks available");
                        } else {
                            for (Task task : service.getTasks()) {
                                System.out.println(task.getName());
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Total tasks: " + service.getTaskCount());
                        break;

                    case 4:
                        service.save(Paths.get("tasks.dat"));
                        System.out.println("Tasks saved successfully");
                        break;

                    case 5:
                        service = TaskService.load(Paths.get("tasks.dat"));
                        System.out.println("Tasks loaded successfully");
                        break;

                    case 6:
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
