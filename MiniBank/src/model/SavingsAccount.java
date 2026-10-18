package MiniBank.src.model;

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private static final long MINIMUM_BALANCE = 500;

    public SavingsAccount(String ownerName, long openingBalance) {
        super(ownerName, openingBalance);
    }

    @Override
    public double interestRate() {
        return 0.04;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && getBalance() - amount >= MINIMUM_BALANCE;
    }

    public long calculateInterest() {
        return Math.round(getBalance() * interestRate());
    }
}
