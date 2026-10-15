import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FolderTreeReport {
    public static void main(String[] args) {
        Path root = Paths.get("testfolder");
        Path report = Paths.get("folder_report.txt");
        List<String> lines = new ArrayList<>();

        lines.add("FOLDER TREE REPORT");
        lines.add("Root Directory: " + root.toAbsolutePath());
        lines.add("");

        try {
            Files.createDirectories(root);
            Files.createDirectories(root.resolve("subfolder"));

            Files.writeString(root.resolve("file1.txt"), "Hello World");
            Files.writeString(root.resolve("subfolder/file2.txt"), "Java NIO");

            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    lines.add("Name: " + file.getFileName());
                    lines.add("Path: " + root.relativize(file));
                    lines.add("Size: " + attrs.size() + " bytes");
                    lines.add("Modified: " + Instant.ofEpochMilli(
                        attrs.lastModifiedTime().toMillis()
                    ));
                    lines.add("");
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(
                    Path file, IOException exc
                ) {
                    lines.add("Unable to access: " + file);
                    return FileVisitResult.CONTINUE;
                }
            });

            Files.write(report, lines);

            System.out.println("Folder report generated successfully.");
            System.out.println("Report saved to: " + report.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}