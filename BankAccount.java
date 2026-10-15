import java.util.*;
public class BankAccount {
    class savings {
        int bankID;
        double balance;
        final int interst = 4;
        double amount;
        savings(int bankID,double balance)
        {
            this.bankID = bankID;
            this.balance = balance;
        }
        void Withdraw(double amount)
        {
            if(amount>=0)
            {
            if(balance-amount>=1000)
            {
                System.out.println("Insufficient Balance");
                return;
            }
            else balance-=amount;
        }
        else return;
        }
        void deposit(double amount)
        {
            if(amount<0)
            {
                System.out.println("Invalid Input!");
                return;
            }
            else
            {
                balance+=amount;
            }
        }
    }
}
