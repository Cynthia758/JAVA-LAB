class FileResource implements AutoCloseable {
    public FileResource() {
        System.out.println("Resource opened.");
    }

    public void process() throws Exception {
        System.out.println("Processing resource...");
        throw new Exception("Original error: Processing failed.");
    }

    @Override
    public void close() {
        System.out.println("Resource closed.");
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {
        try (FileResource resource = new FileResource()) {
            resource.process();
        } catch (Exception e) {
            System.out.println("Caught error: " + e.getMessage());
        } finally {
            System.out.println("Program finished.");
        }
    }
}