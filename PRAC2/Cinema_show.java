package PRAC2;
public class Cinema_show{
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;
    public Cinema_show(String title,int capacity) {
        this.title=title;
        this.capacity=capacity;
        this.seatsAvailable=capacity;
    }
    public Cinema_show(String title) {
        this(title, 100);
    }
    public boolean book(int n) {
        if (n<=seatsAvailable) {
            seatsAvailable -=n;
            totalBooked +=n;
            return true;
        }
        return false;
    }
    public void cancel(int n) {
        seatsAvailable+=n;
        if (seatsAvailable>capacity) {
            seatsAvailable=capacity;
        }
    }
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    public static int getTotalBooked() {
        return totalBooked;
    }
    public static void main(String[] args) {
        Cinema_show show = new Cinema_show("Inception", 50);
        System.out.println("Book 30: " + show.book(30) + " | Seats left: " + show.getSeatsAvailable());
        System.out.println("Book 30: " + show.book(30) + " | Seats left: " + show.getSeatsAvailable());
        show.cancel(10);
        System.out.println("Cancel 10 | Seats left: " + show.getSeatsAvailable());
        System.out.println("Book 15: " + show.book(15) + " | Seats left: " + show.getSeatsAvailable());
        System.out.println("Total Booked: "+Cinema_show.getTotalBooked());
    }
}
