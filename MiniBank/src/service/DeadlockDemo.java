package MiniBank.src.service;

public class DeadlockDemo {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    private static void unsafeTask(Object first, Object second) {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName()
                    + " acquired first lock.");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            synchronized (second) {
                System.out.println(Thread.currentThread().getName()
                        + " acquired both locks.");
            }
        }
    }

    private static void safeTask(Object first, Object second) {
        Object lock1 = System.identityHashCode(first)
                < System.identityHashCode(second) ? first : second;
        Object lock2 = lock1 == first ? second : first;

        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName()
                        + " acquired both locks safely.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating safe lock ordering:");

        Thread t1 = new Thread(
                () -> safeTask(LOCK_A, LOCK_B), "Thread-1");
        Thread t2 = new Thread(
                () -> safeTask(LOCK_B, LOCK_A), "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Both threads completed without deadlock.");
    }
}
