import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySum {
    static final int SIZE = 1_000_000;
    static final int THREADS = 8;
    static long sharedTotal = 0;
    static long synchronizedTotal = 0;
    static AtomicLong atomicTotal = new AtomicLong(0);

    static int[] numbers = new int[SIZE];

    static synchronized void addSynchronized(long value) {
        synchronizedTotal += value;
    }

    static void calculate(int start, int end, int method)
            throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        int chunk = (end - start) / THREADS;
        for (int i = 0; i < THREADS; i++) {
            int from = start + i * chunk;
            int to = (i == THREADS - 1) ? end : from + chunk;

            threads[i] = new Thread(() -> {
                long localSum = 0;

                for (int j = from; j < to; j++) {
                    localSum += numbers[j];
                }

                if (method == 1) {
                    sharedTotal += localSum;
                } else if (method == 2) {
                    addSynchronized(localSum);
                } else {
                    atomicTotal.addAndGet(localSum);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    static long run(int method) throws InterruptedException {
        sharedTotal = 0;
        synchronizedTotal = 0;
        atomicTotal.set(0);

        long startTime = System.nanoTime();
        calculate(0, SIZE, method);
        long endTime = System.nanoTime();

        return switch (method) {
            case 1 -> {
                System.out.println("Unsynchronized sum: " + sharedTotal);
                yield endTime - startTime;
            }
            case 2 -> {
                System.out.println("Synchronized sum: " + synchronizedTotal);
                yield endTime - startTime;
            }
            default -> {
                System.out.println("AtomicLong sum: " + atomicTotal.get());
                yield endTime - startTime;
            }
        };
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }
        long expected = SIZE;
        System.out.println("Expected sum: " + expected);
        long time1 = run(1);
        long time2 = run(2);
        long time3 = run(3);

        System.out.println("\nExecution times:");
        System.out.printf("Unsynchronized: %.3f ms%n", time1 / 1_000_000.0);
        System.out.printf("Synchronized:   %.3f ms%n", time2 / 1_000_000.0);
        System.out.printf("AtomicLong:     %.3f ms%n", time3 / 1_000_000.0);
    }
}
