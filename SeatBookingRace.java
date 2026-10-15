class SeatBooking {
    static int seatsLeft = 5;
    static int successfulBookings = 0;

    static void book() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            seatsLeft--;
            successfulBookings++;
            System.out.println(Thread.currentThread().getName() + " booked a seat.");
        } else {
            System.out.println(Thread.currentThread().getName() + " failed. No seats left.");
        }
    }
}

public class SeatBookingRace {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(SeatBooking::book, "Thread-" + (i + 1));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nSeats left: " + SeatBooking.seatsLeft);
        System.out.println("Successful bookings: " + SeatBooking.successfulBookings);
    }
}
