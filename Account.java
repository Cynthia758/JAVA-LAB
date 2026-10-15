import java.util.*;
public class Account {
    int Choice;
    Account(int Choice)
    {
        this.Choice = Choice;
    }
    class savings {
        String bankID;
        double balance;
        final int interst = 4;
        double amount;
        savings(String ID,double b)
        {
            bankID = ID;
            if(balance>=100)
            this.balance = b;
            else
            {
                System.out.println("Insufficient Amount");
                return;
            }
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
            else
                { balance-=amount;
                    System.out.println("Updated balance : "+balance);
                }
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
                System.out.println("Updated Balance : "+balance);
            }
        }
        void Display()
        {
            System.out.println("Bank ID : "+bankID);
            System.out.println("Bank Balance : "+balance);
            System.out.println("Interest Rate : "+interst+"%");
        }
    }
    class Current
    {
        String bankID;
        double balance;
        final int interst = 0;
        double amount;
        Current(String ID, double b)
        {
            bankID = ID;
            if(balance>=5000)
            balance = b;
            else 
            {
                System.out.println("Insufficient Amount");
                return;
            }
        }
         void Withdraw(double amount)
        {
            if(amount>=0)
            {
            if(balance-amount>=5000)
            {
                System.out.println("Insufficient Balance");
                return;
            }
            else
                { balance-=amount;
                    System.out.println("Updated Balance : "+balance);
                }
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
                System.out.println("Updated Balance : "+balance);
            }
        }
        void Display()
        {
            System.out.println("Bank ID : "+bankID);
            System.out.println("Balance : "+balance);
            System.out.println("Interst Rate : "+interst + "%");
        }
    }
    class Fixed
    {
        String bankID;
        double balance;
        final int interst = 7;
        double amount;
        Fixed(String bankID, double balance)
        {
            this.bankID = bankID;
            this.balance = balance;
        }
        void withdraw()
        {
            System.out.println("Withdrawal is not allowed in Fixed deposit Account");
            return;
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
                System.out.println("Updated Balance : "+balance);
            }
        }
        void Display()
        {
            System.out.println("Bank ID : "+bankID);
            System.out.println("Balance : "+balance);
            System.out.println("Interst Rate : "+interst + "%");
        }

    }
    public static void main(String[] args)
    {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Enter Your Choice(If wrong choice then By default Savings Account) : ");
        System.out.println("1.Press 'S' for Savings Account");
        System.out.println("2.Press 'C' for Current Account");
        System.out.println("3.Press 'F' for Fixed Deposit Account");
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        if(s=="C")
        {
            System.out.println("Choose the operation You want to perform : ");
            System.out.println("Enter W for Withdrawl");
            System.out.println("Enter D for Deposit");
            System.out.println("Enter anything(except W/D) for Display");
            Scanner s3 = new Scanner(System.in);
            String choice = s3.next(); 

            if(choice=="W")
            {
                // customer1.Withdraw(3000);
            }
            else if(choice=="D")
            {
                // customer1.deposit(3000);
            }
            else 
                // customer1.Display();
            s3.close();
           
        }
        else if(s=="F")
        {

        }
        else
        {

        }
        sc.close();
    }
}
