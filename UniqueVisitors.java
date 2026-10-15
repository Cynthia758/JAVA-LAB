import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "Aarav", "Diya", "Riya", "Aarav", "Krish", "Diya", "Meera"
        );

        HashSet<String> uniqueUsers = new HashSet<>(logins);
        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Unique users count: " + uniqueUsers.size());
        System.out.println("First three distinct names:");

        int count = 0;
        for (String name : orderedUsers) {
            if (count == 3) {
                break;
            }
            System.out.println(name);
            count++;
        }
    }
}
