package MiniBank.src.model;

import java.io.Serializable;
import java.util.Objects;
import MiniBank.src.exception.InvalidAmountException;
import MiniBank.src.exception.InsufficientFundsException;
import MiniBank.src.model.annotation.Id;
import MiniBank.src.model.annotation.Positive;

public abstract class Account implements Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextNumber = 1;

    @Id
    private final String accountNumber;
    private final String ownerName;

    @Positive
    private long balance;
    private boolean active;

    protected Account(String ownerName, long openingBalance) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException(
                "Opening balance cannot be negative"
            );
        }
        synchronized (Account.class) {
            this.accountNumber = String.format("AC%04d", nextNumber++);
        }
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public static synchronized void updateNextNumber(Account[] accounts) {
        int maxNumber = 0;
        for (Account account : accounts) {
            if (account != null) {
                String number = account.getAccountNumber();
                if (number != null && number.startsWith("AC")) {
                    try {
                        int value = Integer.parseInt(number.substring(2));
                        maxNumber = Math.max(maxNumber, value);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        nextNumber = maxNumber + 1;
    }
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);
    public synchronized void deposit(long amount)
            throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                "Deposit amount must be positive"
            );
        }

        try {
            balance = Math.addExact(balance, amount);
        } catch (ArithmeticException e) {
            throw new InvalidAmountException("Balance limit exceeded");
        }
    }
    public synchronized boolean withdraw(long amount)
            throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                "Withdrawal amount must be positive"
            );
        }

        if (!active || !canWithdraw(amount)) {
            long shortfall = Math.max(0, amount - balance);

            throw new InsufficientFundsException(
                "Withdrawal denied by account rules or insufficient funds",
                shortfall
            );
        }

        balance = Math.subtractExact(balance, amount);
        return true;
    }

    public boolean transfer(Account destination, long amount)
            throws InvalidAmountException, InsufficientFundsException {
        if (destination == null || destination == this) {
            throw new IllegalArgumentException(
                "Destination account is invalid"
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                "Transfer amount must be positive"
            );
        }

        Account first = accountNumber.compareTo(
            destination.accountNumber
        ) < 0 ? this : destination;

        Account second = first == this ? destination : this;

        synchronized (first) {
            synchronized (second) {
                if (!active || !destination.active || !canWithdraw(amount)) {
                    throw new InsufficientFundsException(
                        "Transfer denied by account rules or insufficient funds",
                        Math.max(0, amount - balance)
                    );
                }

                try {
                    long newSourceBalance =
                        Math.subtractExact(balance, amount);

                    long newDestinationBalance =
                        Math.addExact(destination.balance, amount);

                    balance = newSourceBalance;
                    destination.balance = newDestinationBalance;

                    return true;
                } catch (ArithmeticException e) {
                    throw new InvalidAmountException(
                        "Transfer would exceed the balance limit"
                    );
                }
            }
        }
    }
    public synchronized long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public int compareTo(Account other) {
        return Long.compare(getBalance(), other.getBalance());
    }
    @Override
    public String toString() {
        return accountNumber + " | " + ownerName
            + " | Balance: " + getBalance();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account other)) {
            return false;
        }
        return accountNumber.equals(other.accountNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}