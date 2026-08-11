package card;
public class Driver2 {
    public static void main(String[] args) {
        Card[] incoming = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades")
        };
        Card[] cards = new Card[incoming.length];
        int size = 0;
        for (int i = 0; i < incoming.length; i++) {
            boolean duplicate = false;
            for (int j = 0; j < size; j++) {
                if (incoming[i].equals(cards[j])) {
                    System.out.println("Duplicate found: " + incoming[i]);
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                break;
            }
            cards[size] = incoming[i];
            size++;
        }
    }
}
