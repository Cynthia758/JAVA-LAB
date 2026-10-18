
package MiniBank.src.service;

import MiniBank.src.model.Account;
import MiniBank.src.exception.InvalidAmountException;
import MiniBank.src.exception.InsufficientFundsException;

public class AccountWorker implements Runnable {
    private final Account account;
    private final int operations;

    public AccountWorker(Account account, int operations) {
        this.account = account;
        this.operations = operations;
    }

    @Override
    public void run() {
        for (int i = 0; i < operations; i++) {
            try {
                account.deposit(100);
                account.withdraw(100);
            } catch (InvalidAmountException | InsufficientFundsException e) {
                System.out.println(
                    Thread.currentThread().getName() + ": " + e.getMessage()
                );
            }
        }

        System.out.println(
            Thread.currentThread().getName()
            + " finished. Balance: " + account.getBalance()
        );
    }
}
