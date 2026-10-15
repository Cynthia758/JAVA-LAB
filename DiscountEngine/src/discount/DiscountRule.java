package DiscountEngine.src.discount;

@FunctionalInterface
public interface DiscountRule {
    double apply(double price);
}
