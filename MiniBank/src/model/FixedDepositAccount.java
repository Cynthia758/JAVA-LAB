package MiniBank.src.model;

public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final int durationMonths;

    public FixedDepositAccount(String ownerName, long openingBalance, int durationMonths) {
        super(ownerName, openingBalance);

        if (durationMonths <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }

        this.durationMonths = durationMonths;
    }

    @Override
    public double interestRate() {
        return 0.07;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public long calculateMaturityInterest() {
        return Math.round(getBalance() * interestRate() * durationMonths / 12.0);
    }
}
