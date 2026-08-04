import java.util.Objects;
import java.util.Scanner;

class Customer implements Cloneable {

    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;

    private static long customerCounter = 101;

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

    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = generateCustomerId();
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

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
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
        return accountNumber + " | " + ownerName + " | Balance = ₹" + balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Account a = (Account) obj;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

public class MiniBank {

    record BankInfo(String name, String branch) {
    }

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Main Branch");

        Customer.Address address =
                new Customer.Address("12 MG Road", "Ahmedabad", "380001");

        Customer c1 = new Customer(
                "Vaidehi",
                "vaidehi@gmail.com",
                "1234567890",
                address);

        Customer copy = c1.clone();

        Account[] accounts = {
                new Account(c1.getName(), 5000),
                new Account("Riya"),
                new Account("Rahul", 10000)
        };

        System.out.println("Customer Details");
        System.out.println("ID: " + c1.getCustomerId());
        System.out.println("Name: " + c1.getName());
        System.out.println("Email: " + c1.getEmail());
        System.out.println("Mobile: " + c1.getMobile());
        System.out.println("City: " + c1.getAddress().getCity());

        System.out.println("\nTransactions");

        accounts[0].deposit(1000);
        accounts[1].deposit(3000);

        System.out.println("Withdraw ₹2000 from Account 1: "
                + accounts[0].withdraw(2000));

        System.out.println("Withdraw ₹4000 from Account 3: "
                + accounts[2].withdraw(4000));

        System.out.println("\nAccount Details");

        for (Account acc : accounts) {
            System.out.println(acc);
        }

        System.out.println("\nEquals Demo");

        System.out.println(accounts[0].equals(accounts[1]));
        System.out.println(accounts[0].equals(accounts[0]));

        System.out.println("\nInstanceof Demo");

        Object obj = accounts[0];

        if (obj instanceof Account) {
            System.out.println("Object is an Account.");
        }

        System.out.println("\nClone Demo");

        System.out.println("Original Customer : " + c1.getName());
        System.out.println("Cloned Customer   : " + copy.getName());

        System.out.println("\n================================");
        System.out.println(bank.name() + " - " + bank.branch());
        System.out.println("================================");

        while (true) {

            System.out.println("\nMenu");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid choice!");
                continue;
            }

            switch (option) {

                case OPEN_ACCOUNT ->
                        System.out.println("Open Account - To be implemented in a later lab.");

                case DEPOSIT ->
                        System.out.println("Deposit - To be implemented in a later lab.");

                case WITHDRAW ->
                        System.out.println("Withdraw - To be implemented in a later lab.");

                case TRANSFER ->
                        System.out.println("Transfer - To be implemented in a later lab.");

                case EXIT -> {
                    System.out.println("Thank you for using MiniBank!");
                    sc.close();
                    return;
                }
            }
        }
    }
}