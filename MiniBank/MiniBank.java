package MiniBank;

import MiniBank.src.exception.InvalidAmountException;
import MiniBank.src.exception.InsufficientFundsException;
import MiniBank.src.exception.BankException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;
import MiniBank.src.model.CurrentAccount;
import MiniBank.src.model.FixedDepositAccount;
import MiniBank.src.model.annotation.MaxLength;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import MiniBank.src.util.StatePersister;
import MiniBank.src.service.BankService;
import MiniBank.src.util.Repository;
import MiniBank.src.exception.AccountNotFoundException;
import MiniBank.src.util.ReportGenerator;
import MiniBank.src.util.TransactionLog;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, REPORT, EXIT
}

enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER
}

record Command(TransactionType type, String accountNumber, long amount) {
}

interface Transactable {
    void deposit(long amount);

    boolean withdraw(long amount);
}

interface InterestBearing {
    double interestRate();

    default double yearlyInterest(Account account) {
        return account.getBalance() * interestRate();
    }
}

@FunctionalInterface
interface WithdrawRule {
    boolean allow(Account account, long amount);
}

interface Premium {
}

class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static long customerCounter = 100;

    private final String customerId;

    @MaxLength(100)
    private final String name;

    @MaxLength(150)
    private final String email;

    private final String mobile;
    private Address address;

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }

    public Customer(String name, String email, String mobile, Address address) {
        synchronized (Customer.class) {
            this.customerId = "CUST" + (++customerCounter);
        }
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public Customer clone() {
        try {
            Customer copy = (Customer) super.clone();

            if (address != null) {
                copy.address = new Address(
                        address.getLine(),
                        address.getCity(),
                        address.getPincode());
            }

            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public static class Address implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String line;
        private final String city;
        private final String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() {
            return line;
        }

        public String getCity() {
            return city;
        }

        public String getPincode() {
            return pincode;
        }
    }
}

public class MiniBank {
    private static final BankInfo BANK = new BankInfo("MiniBank", "Main Branch");

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Map<String, Account> ACCOUNTS = new ConcurrentHashMap<>();

    private static final List<String> TRANSACTION_HISTORY = new ArrayList<>();

    private static final Repository<Account> REPOSITORY = new Repository<>();

    private static final BankService BANK_SERVICE = new BankService(REPOSITORY);

    private static final Path ACCOUNT_FILE = Path.of("MiniBank", "data", "accounts.dat");

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println(BANK);
        System.out.println("================================");

        loadAccounts();

        boolean running = true;

        while (running) {
            displayMenu();
            Integer choice = readInt("Enter your choice: ");

            if (choice == null) {
                continue;
            }

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.REPORT;
                case 6 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid choice. Select 1 to 5.");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT -> openAccount();
                case DEPOSIT -> deposit();
                case WITHDRAW -> withdraw();
                case TRANSFER -> transfer();
                case REPORT -> generateReport();
                case EXIT -> {
                    saveAccounts();
                    running = false;
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Generate Report");
        System.out.println("6. Exit");
    }

    private static Integer readInt(String prompt) {
        System.out.print(prompt);
        String input = SCANNER.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid whole number.");
            return null;
        }
    }

    private static Long readLong(String prompt) {
        System.out.print(prompt);
        String input = SCANNER.nextLine().trim();

        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println(
                    "Please enter a valid whole-number amount.");
            return null;
        }
    }

    private static void openAccount() {
        System.out.print("Owner name: ");
        String name = SCANNER.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Owner name cannot be empty.");
            return;
        }

        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Fixed Deposit Account");

        Integer type = readInt("Choose account type: ");
        if (type == null) {
            return;
        }

        Long balance = readLong("Opening balance: Rs. ");
        if (balance == null || balance < 0) {
            System.out.println("Opening balance cannot be negative.");
            return;
        }

        try {
            Account account = BANK_SERVICE.openAccount(name, balance, type);

            ACCOUNTS.put(account.getAccountNumber(), account);
            TRANSACTION_HISTORY.add(
                    "OPEN " + account.getAccountNumber() + " " + balance);

            System.out.println("Account created successfully.");
            System.out.println(account);

        } catch (InvalidAmountException | IllegalArgumentException e) {
            System.out.println("Account creation failed: " + e.getMessage());
        }
    }

    private static Account findAccount(String prompt) {
        System.out.print(prompt);
        String accountNumber = SCANNER.nextLine().trim();

        Account account = ACCOUNTS.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
        }

        return account;
    }

    private static void deposit() {
        Account account = findAccount("Account number: ");
        if (account == null) {
            return;
        }

        Long amount = readLong("Deposit amount: Rs. ");
        if (amount == null) {
            return;
        }

        try {
            BANK_SERVICE.deposit(account.getAccountNumber(), amount);

            TRANSACTION_HISTORY.add(
                    "DEPOSIT " + account.getAccountNumber() + " " + amount);

            logTransaction("DEPOSIT " + account.getAccountNumber() + " " + amount
);

            System.out.println("Deposit successful.");
            System.out.println(account);

        } catch (AccountNotFoundException | InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }

    private static void withdraw() {
        Account account = findAccount("Account number: ");
        if (account == null) {
            return;
        }

        Long amount = readLong("Withdrawal amount: Rs. ");
        if (amount == null) {
            return;
        }

        try {
            BANK_SERVICE.withdraw(account.getAccountNumber(), amount);

            TRANSACTION_HISTORY.add(
                    "WITHDRAW " + account.getAccountNumber() + " " + amount);

            logTransaction("WITHDRAW " + account.getAccountNumber() + " " + amount
);

            System.out.println("Withdrawal successful.");
            System.out.println(account);

        } catch (AccountNotFoundException
                | InvalidAmountException
                | InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private static void transfer() {
        Account source = findAccount("Source account number: ");
        if (source == null) {
            return;
        }

        Account destination = findAccount("Destination account number: ");
        if (destination == null) {
            return;
        }

        Long amount = readLong("Transfer amount: Rs. ");
        if (amount == null) {
            return;
        }

        try {
            BANK_SERVICE.transfer(
                    source.getAccountNumber(),
                    destination.getAccountNumber(),
                    amount);

            TRANSACTION_HISTORY.add(
                    "TRANSFER " + source.getAccountNumber() + " "
                            + destination.getAccountNumber() + " " + amount);
            
            logTransaction("TRANSFER " + source.getAccountNumber() + " " +destination.getAccountNumber() + " " + amount);

            System.out.println("Transfer successful.");
            System.out.println(source);
            System.out.println(destination);

        } catch (AccountNotFoundException
                | InvalidAmountException
                | InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());

        } finally {
            System.out.println("Transfer operation completed.");
        }
    }

    public static ArrayList<Account> sortedByBalance() {
        ArrayList<Account> result = new ArrayList<>(ACCOUNTS.values());
        result.sort(Comparator.comparingLong(Account::getBalance));
        return result;
    }

    public static Map<String, Account> getAccounts() {
        return new LinkedHashMap<>(ACCOUNTS);
    }

    public static List<String> getTransactionHistory() {
        return new ArrayList<>(TRANSACTION_HISTORY);
    }

    private static void loadAccounts() {
        if (!java.nio.file.Files.exists(ACCOUNT_FILE)) {
            System.out.println("No saved accounts found.");
            return;
        }

        try {
            Account[] savedAccounts = StatePersister.load(ACCOUNT_FILE);

            for (Account account : savedAccounts) {
                ACCOUNTS.put(account.getAccountNumber(), account);
                REPOSITORY.save(account);
            }

            Account.updateNextNumber(savedAccounts);

            System.out.println("Saved accounts loaded: " + ACCOUNTS.size());

        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("Could not load saved accounts: " + e.getMessage());
        }
    }

    private static void saveAccounts() {
        try {
            StatePersister.save(
                    ACCOUNTS.values().toArray(new Account[0]),
                    ACCOUNT_FILE);

            System.out.println("Accounts saved successfully.");
        } catch (java.io.IOException e) {
            System.out.println("Could not save accounts: " + e.getMessage());
        }
    }

    private static void generateReport() {
        Path logDirectory = Path.of("MiniBank", "logs");
        Path reportFile = Path.of(
                "MiniBank", "data", "transaction-report.txt");

        try {
            ReportGenerator.generate(logDirectory, reportFile);
        } catch (IOException | ArithmeticException e) {
            System.out.println(
                    "Report generation failed: " + e.getMessage());
        }
    }

    private static void logTransaction(String line) {
        Path logFile = Path.of("MiniBank", "logs", "transactions.log");

        try {
            TransactionLog.append(logFile, line);
        } catch (IOException e) {
            System.out.println("Could not write transaction log: "
                    + e.getMessage());
        }
    }

}
