
package MiniBank.src.util;

import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;

public class RepositoryTest {

    public static void main(String[] args) {
        Repository<Account> repository = new Repository<>();

        Account a1 = new SavingsAccount("Vaidehi", 5000);
        Account a2 = new SavingsAccount("Jiya", 8000);

        repository.save(a1);
        repository.save(a2);

        System.out.println("Saved account:");
        System.out.println(repository.findById(a1.getAccountNumber()));

        System.out.println("\nAll accounts:");
        for (Account account : repository.findAll()) {
            System.out.println(account);
        }

        System.out.println("\nTotal accounts: " + repository.findAll().size());

        boolean deleted = repository.delete(a1.getAccountNumber());
        System.out.println("\nAccount deleted: " + deleted);
        System.out.println("Remaining accounts: " + repository.findAll().size());

        Integer[] numbers = {10, 20, 30};
        System.out.println("\nAverage: " + Repository.average(numbers));
    }
}
