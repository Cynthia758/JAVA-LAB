package MiniBank.src.service;

import java.util.ArrayList;
import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;
import MiniBank.src.model.CurrentAccount;
import MiniBank.src.model.FixedDepositAccount;
import MiniBank.src.util.Repository;
import MiniBank.src.util.Validator;
import MiniBank.src.util.StatementFormatter;
import MiniBank.src.exception.AccountNotFoundException;
import MiniBank.src.exception.InvalidAmountException;
import MiniBank.src.exception.InsufficientFundsException;

public class BankService {
    private final Repository<Account> repository;
    public BankService(Repository<Account> repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    public Account openAccount(
            String name, long balance, int type)
            throws InvalidAmountException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        if (balance < 0) {
            throw new InvalidAmountException(
                "Opening balance cannot be negative"
            );
        }
        Account account;
        switch (type) {
            case 1 -> account = new SavingsAccount(name, balance);
            case 2 -> account = new CurrentAccount(name, balance, 5000);
            case 3 -> account = new FixedDepositAccount(name, balance, 12);
            default -> throw new IllegalArgumentException(
                "Invalid account type"
            );
        }
        repository.save(account);
        return account;
    }
    public void deposit(String accountNumber, long amount)
            throws AccountNotFoundException, InvalidAmountException {
        Account account = findAccount(accountNumber);
        account.deposit(amount);
    }
    public void withdraw(String accountNumber, long amount)
            throws AccountNotFoundException,
                   InvalidAmountException,
                   InsufficientFundsException {

        Account account = findAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(
            String fromNumber, String toNumber, long amount)
            throws AccountNotFoundException,
                   InvalidAmountException,
                   InsufficientFundsException {
        Account from = findAccount(fromNumber);
        Account to = findAccount(toNumber);

        from.transfer(to, amount);
    }

    public String statement(String accountNumber)
            throws AccountNotFoundException {

        Account account = findAccount(accountNumber);
        return StatementFormatter.buildStatement(account);
    }
    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }
    private Account findAccount(String accountNumber)
            throws AccountNotFoundException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new AccountNotFoundException(
                "Account number cannot be empty"
            );
        }
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(
                "Account not found: " + accountNumber
            );
        }
        return account;
    }
}
