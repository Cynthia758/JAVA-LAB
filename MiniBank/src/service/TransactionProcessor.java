package MiniBank.src.service;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor implements AutoCloseable {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    public void submit(Runnable task) {
        executor.submit(task);
    }
    public void submitBatch(List<Runnable> batch) {
        for (Runnable task : batch) {
            submit(task);
        }
    }
    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        stop();
    }

    @Override
    public void close() {
        stop();
    }
}
