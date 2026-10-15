public class CounterRace {
    static int counter = 0;
    static final int THREADS = 10;
    static final int INCREMENTS = 100000;

    static void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    increment();
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int expected = THREADS * INCREMENTS;

        System.out.println("Expected count: " + expected);
        System.out.println("Actual count: " + counter);
    }
}
