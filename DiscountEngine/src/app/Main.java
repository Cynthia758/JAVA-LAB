package DiscountEngine.src.app;
import DiscountEngine.src.discount.DiscountRule;
import java.util.*;
public class Main {
    public static void main(String[] args) {

        List<Double> prices = Arrays.asList(
            1000.0,
            2500.0,
            5000.0,
            7500.0
        );
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose discount:");
        System.out.println("1. 10% discount");
        System.out.println("2. 20% discount");
        System.out.println("3. Flat ₹500 discount");
        int choice = sc.nextInt();
        DiscountRule rule;
        switch (choice) {
            case 1:
                rule = price -> price * 0.90;
                break;

            case 2:
                rule = price -> price * 0.80;
                break;

            case 3:
                rule = price -> Math.max(0, price - 500);
                break;

            default:
                rule = price -> price;
        }

        for (double price : prices) {
            System.out.println(
                "Original: " + price +
                " -> Final: " + rule.apply(price)
            );
        }

        sc.close();
    }
}
