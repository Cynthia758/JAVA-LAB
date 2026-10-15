import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) {
        Path[] files = {
            Paths.get("log1.txt"),
            Paths.get("log2.txt"),
            Paths.get("log3.txt")
        };

        String keyword = "ERROR";
        int totalLines = 0;
        int matchingLines = 0;
        try {
            String[] logs = {
                "INFO Application started\nERROR Database connection failed\nINFO Retrying connection",
                "INFO User logged in\nERROR File not found\nWARNING Low memory",
                "INFO Task completed\nINFO Application closed\nERROR Network failure"
            };

            for (int i = 0; i < files.length; i++) {
                Files.write(files[i], logs[i].getBytes());
            }

            for (Path file : files) {
                int fileLines = 0;
                int fileMatches = 0;

                List<String> lines = Files.readAllLines(file);

                for (String line : lines) {
                    fileLines++;

                    if (line.contains(keyword)) {
                        fileMatches++;
                    }
                }

                totalLines += fileLines;
                matchingLines += fileMatches;

                BasicFileAttributes attributes = Files.readAttributes(
                    file, BasicFileAttributes.class
                );

                System.out.println("File: " + file.getFileName());
                System.out.println("Size: " + attributes.size() + " bytes");
                System.out.println("Total lines: " + fileLines);
                System.out.println("Matching lines: " + fileMatches);
                System.out.println();
            }

            System.out.println("Total lines across all files: " + totalLines);
            System.out.println("Total lines containing \"" + keyword + "\": " + matchingLines);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
