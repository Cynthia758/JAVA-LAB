
package MiniBank.src.util;

class BankResource implements AutoCloseable {

    public BankResource() {
        System.out.println("Bank resource opened.");
    }

    public void performOperation() {
        System.out.println("Performing banking operation.");
    }

    @Override
    public void close() {
        System.out.println("Bank resource closed automatically.");
    }
}

public class AutoCloseableDemo {

    public static void main(String[] args) {
        try (BankResource resource = new BankResource()) {
            resource.performOperation();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Demo completed.");
    }
}
