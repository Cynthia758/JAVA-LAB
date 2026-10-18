package MiniBank.src.util;

import java.nio.file.Path;

import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;
import MiniBank.src.model.CurrentAccount;
import MiniBank.src.model.FixedDepositAccount;

public class PersistenceDemo {

    public static void main(String[] args) {
        Path file = Path.of("MiniBank", "data", "accounts.dat");

        Account[] accounts = {
            new SavingsAccount("Vaidehi", 5000),
            new CurrentAccount("Aarav", 10000, 5000),
            new FixedDepositAccount("Diya", 20000, 12)
        };

        try {
            StatePersister.save(accounts, file);
            System.out.println("Accounts saved successfully.");

            Account[] loadedAccounts = StatePersister.load(file);

            System.out.println("Accounts loaded successfully:");

            for (Account account : loadedAccounts) {
                System.out.println(account);
            }
        } catch (Exception e) {
            System.out.println("Persistence error: " + e.getMessage());
        }
    }
}
