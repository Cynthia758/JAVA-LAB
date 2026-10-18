package MiniBank.src.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class ReportGenerator {

    public static void generate(Path logDirectory, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int transactionCount = 0;

        Files.createDirectories(logDirectory);
        Path parent = reportFile.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (DirectoryStream<Path> files =
                Files.newDirectoryStream(logDirectory, "*.log")) {

            for (Path file : files) {
                BasicFileAttributes attributes =
                    Files.readAttributes(file, BasicFileAttributes.class);

                if (!attributes.isRegularFile()) {
                    continue;
                }

                try (BufferedReader reader =
                        Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length < 3) {
                            continue;
                        }

                        try {
                            long amount =
                                Long.parseLong(parts[parts.length - 1]);

                            if (amount < 0) {
                                continue;
                            }

                            if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits = Math.addExact(
                                    totalDeposits, amount
                                );
                                transactionCount++;
                            } else if (
                                parts[0].equalsIgnoreCase("WITHDRAW")
                            ) {
                                totalWithdrawals = Math.addExact(
                                    totalWithdrawals, amount
                                );
                                transactionCount++;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(
                                "Skipping invalid transaction: " + line
                            );
                        }
                    }
                }
            }
        }

        String report = """
                MiniBank Transaction Report
                ===========================
                Transactions counted: %d
                Total deposits: Rs. %d
                Total withdrawals: Rs. %d
                Net movement: Rs. %d
                """.formatted(
                    transactionCount,
                    totalDeposits,
                    totalWithdrawals,
                    totalDeposits - totalWithdrawals
                );

        Files.writeString(reportFile, report);
        System.out.println(report);
        System.out.println("Report saved to: " + reportFile);
    }

    public static void main(String[] args) {
        Path logDirectory = Path.of("MiniBank", "logs");
        Path reportFile = Path.of(
            "MiniBank", "data", "transaction-report.txt"
        );

        try {
            generate(logDirectory, reportFile);
        } catch (IOException | ArithmeticException e) {
            System.out.println("Report generation failed: " + e.getMessage());
        }
    }
}
