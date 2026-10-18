package MiniBank.src.util;

import MiniBank.src.model.Command;
import MiniBank.src.model.TransactionType;

public class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "Expected: DEPOSIT AC0001 500"
            );
        }

        TransactionType type;

        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type.");
        }

        if (type == TransactionType.TRANSFER) {
            throw new IllegalArgumentException(
                "TRANSFER requires source and destination account numbers."
            );
        }

        long amount;

        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a whole number.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        return new Command(type, parts[1], amount);
    }
}
