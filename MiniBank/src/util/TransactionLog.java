package MiniBank.src.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class TransactionLog {
    private TransactionLog() {
    }
    public static void append(Path file, String transaction)
            throws IOException {
        Path parent = file.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(
            file,
            transaction + System.lineSeparator(),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }
    public static void main(String[] args) {
        Path logFile = Path.of("MiniBank", "logs", "transactions.log");
        try {
            append(logFile, "DEPOSIT AC0001 1000");
            append(logFile, "WITHDRAW AC0001 200");
            System.out.println("Transaction records appended.");
            System.out.println("Log location: " + logFile);
        } catch (IOException e) {
            System.out.println("Could not write transaction log: "
                    + e.getMessage());
        }
    }
}
