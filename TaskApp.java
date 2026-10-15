import java.util.Scanner;

import src.service.TaskService;

public class TaskApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskService service = new TaskService();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Count Tasks");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    service.addTask(sc.nextLine());
                    System.out.println("Task added successfully");
                    break;

                case 2:
                    for (int i = 0; i < service.getTaskCount(); i++) {
                        System.out.println((i + 1) + ". " + service.getTask(i).getName());
                    }
                    break;

                case 3:
                    System.out.println("Total tasks: " + service.getTaskCount());
                    break;

                case 4:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
