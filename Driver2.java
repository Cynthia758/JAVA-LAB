import java.util.Objects;

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Card c = (Card) obj;
        return rank.equals(c.rank) && suit.equals(c.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

public class Driver2 {

    public static void main(String[] args) {

        Card[] cards = new Card[5];

        Card[] incoming = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades")
        };

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

            if (duplicate)
                break;

            cards[size++] = incoming[i];
        }
    }
}