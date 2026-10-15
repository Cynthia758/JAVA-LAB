import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Objects;

public class MiniBank {

    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("MiniBank", "Anand Branch");
        System.out.println("=================================");
        System.out.println(bankInfo);
        System.out.println("=================================");

        Account[] accounts = {
            new Account("Vaidehi", 1000),
            new Account("Riya"),
            new Account("Aarav", 5000)
        };

        accounts[0].deposit(2000);
        accounts[0].withdraw(500);
        accounts[1].deposit(3000);
        accounts[1].withdraw(1000);
        accounts[2].deposit(1500);
        accounts[2].withdraw(2000);

        System.out.println("\nAccount Details:");
        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\nAccount Comparison:");
        System.out.println(accounts[0].equals(accounts[1]));
        System.out.println(accounts[0] instanceof Account);

        System.out.println("\nValidators:");
        System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile invalid: " + Validator.isValidMobile("1234567890"));

        System.out.println("Email valid: " + Validator.isValidEmail("user@gmail.com"));
        System.out.println("Email invalid: " + Validator.isValidEmail("user@gmail"));

        System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN invalid: " + Validator.isValidPan("ABC1234F"));

        System.out.println("IFSC valid: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC invalid: " + Validator.isValidIfsc("SBI1234"));

        System.out.println("Amount valid: " + Validator.isValidAmount("500"));
        System.out.println("Amount invalid: " + Validator.isValidAmount("-500"));

        Command command = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("\nParsed Command:");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        System.out.println("\nStatement:");
        System.out.println(StatementFormatter.buildStatement(accounts[0]));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MiniBank Menu =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid choice.");
                continue;
            }

            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab";
                case DEPOSIT -> "Deposit — to be implemented in a later lab";
                case WITHDRAW -> "Withdraw — to be implemented in a later lab";
                case TRANSFER -> "Transfer — to be implemented in a later lab";
                case EXIT -> "Exiting MiniBank...";
            };

            System.out.println(message);

            if (option == MenuOption.EXIT) {
                break;
            }
        }

        System.out.println("Thank you for using MiniBank. Goodbye!");
        scanner.close();
    }
}

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private static long customerCounter = 100;

    private Address address;

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
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

    public String getCustomerId() {
        return customerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Address {
        private String line;
        private String city;
        private String pincode;

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

class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++accountCounter);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
               ", Owner: " + ownerName +
               ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Account)) {
            return false;
        }

        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

class Validator {
    private static final Pattern MOBILE =
            Pattern.compile("^[6-9][0-9]{9}$");

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PAN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    private static final Pattern IFSC =
            Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    private static final Pattern AMOUNT =
            Pattern.compile("^[1-9][0-9]*$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC.matcher(ifsc).matches();
    }

    public static boolean isValidAmount(String amount) {
        return amount != null && AMOUNT.matcher(amount).matches();
    }
}

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}

record Command(TransactionType type, String accountNumber, long amount) {
}

class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid command");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Error: command must contain exactly 3 parts"
            );
        }

        TransactionType type;

        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: invalid transaction type");
        }

        if (!Validator.isValidAmount(parts[2])) {
            throw new IllegalArgumentException("Error: invalid amount");
        }

        long amount;

        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: amount is too large");
        }

        return new Command(type, parts[1], amount);
    }
}

class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder statement = new StringBuilder();

        statement.append("========== Account Statement ==========\n");
        statement.append("Account Number: ")
                  .append(account.getAccountNumber())
                  .append("\n");
        statement.append("Owner Name: ")
                  .append(account.getOwnerName())
                  .append("\n");
        statement.append("Balance: ")
                  .append(account.getBalance())
                  .append("\n");
        statement.append("Status: ")
                  .append(account.isActive() ? "Active" : "Inactive")
                  .append("\n");
        statement.append("=======================================\n");

        return statement.toString();
    }
}