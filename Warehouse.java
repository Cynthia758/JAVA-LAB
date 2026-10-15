import java.util.*;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(int shortfall) {
        super("Out of stock! Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than zero."
            );
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item + "(s) successfully.");
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        String[] items = {"Laptop", "Mouse", "Keyboard", "Laptop", "Mouse"};
        int[] quantities = {2, 15, 0, 5, -3};

        for (int i = 0; i < items.length; i++) {
            try {
                warehouse.issue(items[i], quantities[i]);
            } catch (OutOfStockException e) {
                System.out.println(items[i] + ": " + e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println(items[i] + ": " + e.getMessage());
            }
        }

        System.out.println("All requests processed.");
    }
}
