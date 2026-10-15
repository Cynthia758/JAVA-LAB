public class Task {
    private String name;

    public Task(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid task name");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
