package src.service;
import src.model.Task;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String name) {
        tasks.add(new Task(name));
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task index");
        }
        return tasks.get(index);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void save(Path path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                Files.newOutputStream(path))) {
            out.writeObject(this);
        }
    }

    public static TaskService load(Path path)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                Files.newInputStream(path))) {
            return (TaskService) in.readObject();
        }
    }
}
