package MiniBank.src.util;
import MiniBank.src.model.Account;

public class StatementFormatter {
    public static String buildStatement(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        StringBuilder statement = new StringBuilder();

        statement.append("========== MINI BANK STATEMENT ==========\n");
        statement.append("Account Number: ")
                .append(account.getAccountNumber()).append('\n');
        statement.append("Owner: ")
                .append(account.getOwnerName()).append('\n');
        statement.append("Balance: Rs. ")
                .append(account.getBalance()).append('\n');
        statement.append("Interest Rate: ")
                .append(account.interestRate()).append("%\n");
        statement.append("Active: ")
                .append(account.isActive()).append('\n');
        statement.append("=========================================");

        return statement.toString();
    }
}