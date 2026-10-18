
package MiniBank.src.service;

import java.util.LinkedList;
import java.util.Queue;

public class TransactionQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public TransactionQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Capacity must be positive"
            );
        }
        this.capacity = capacity;
    }

    public synchronized void produce(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }

    public synchronized T consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        T item = queue.remove();
        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}
