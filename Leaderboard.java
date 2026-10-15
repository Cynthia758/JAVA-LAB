import java.util.*;
public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Aarav", 950);
        scores.put("Diya", 1200);
        scores.put("Krish", 950);
        scores.put("Meera", 1500);
        scores.put("Riya", 1200);
        scores.put("Vivaan", 800);

        int n = 3;
        List<Map.Entry<String, Integer>> leaderboard =
            new ArrayList<>(scores.entrySet());

        leaderboard.sort((a, b) -> {
            int result = b.getValue().compareTo(a.getValue());
            if (result == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return result;
        });

        System.out.println("Top " + n + " Players:");

        for (int i = 0; i < Math.min(n, leaderboard.size()); i++) {
            Map.Entry<String, Integer> player = leaderboard.get(i);
            System.out.println(player.getKey() + " - " + player.getValue());
        }
    }
}