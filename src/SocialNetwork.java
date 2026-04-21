import java.util.*;

public class SocialNetwork {

    static Map<String, Set<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  🌐  SOCIAL NETWORK FRIEND SUGGESTION  🌐 ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Preload sample users and connections
        preloadNetwork();

        boolean running = true;
        while (running) {
            System.out.println("\n┌──────────────────────────────────────┐");
            System.out.println("│               MAIN MENU              │");
            System.out.println("├──────────────────────────────────────┤");
            System.out.println("│  1. Add User                         │");
            System.out.println("│  2. Add Friendship (Connection)      │");
            System.out.println("│  3. View Friends of a User           │");
            System.out.println("│  4. Friend Suggestions (BFS)         │");
            System.out.println("│  5. All Connected Users (DFS)        │");
            System.out.println("│  6. Display Network Graph            │");
            System.out.println("│  7. Exit                             │");
            System.out.println("└──────────────────────────────────────┘");
            System.out.print("  Enter choice: ");

            int choice = -1;
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("  ❌ Invalid input!"); continue; }

            switch (choice) {
                case 1 -> addUser(sc);
                case 2 -> addFriendship(sc);
                case 3 -> viewFriends(sc);
                case 4 -> friendSuggestions(sc);
                case 5 -> dfsTraversal(sc);
                case 6 -> displayNetwork();
                case 7 -> { System.out.println("\n  👋 Goodbye!"); running = false; }
                default -> System.out.println("  ❌ Invalid choice!");
            }
        }
        sc.close();
    }

    static void preloadNetwork() {
        // Add users
        String[] users = {"Arjun", "Priya", "Rohit", "Sneha", "Karthik", "Divya", "Vikram"};
        for (String u : users) graph.put(u, new LinkedHashSet<>());

        // Add connections
        connect("Arjun", "Priya");
        connect("Arjun", "Rohit");
        connect("Priya", "Sneha");
        connect("Priya", "Karthik");
        connect("Rohit", "Divya");
        connect("Sneha", "Vikram");
        connect("Karthik", "Vikram");

        System.out.println("  ✅ Sample network loaded with " + users.length + " users.");
    }

    static void connect(String a, String b) {
        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    static void addUser(Scanner sc) {
        System.out.print("\n  Enter new user name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("  ❌ Name cannot be empty!"); return; }
        if (graph.containsKey(name)) { System.out.println("  ❌ User already exists!"); return; }
        graph.put(name, new LinkedHashSet<>());
        System.out.println("  ✅ User '" + name + "' added to network!");
    }

    static void addFriendship(Scanner sc) {
        System.out.println("\n━━━━━━━━━━  ADD FRIENDSHIP  ━━━━━━━━━━");
        System.out.print("  Enter User 1: ");
        String u1 = sc.nextLine().trim();
        System.out.print("  Enter User 2: ");
        String u2 = sc.nextLine().trim();

        if (!graph.containsKey(u1)) { System.out.println("  ❌ User '" + u1 + "' not found!"); return; }
        if (!graph.containsKey(u2)) { System.out.println("  ❌ User '" + u2 + "' not found!"); return; }
        if (u1.equals(u2)) { System.out.println("  ❌ Cannot connect user to themselves!"); return; }
        if (graph.get(u1).contains(u2)) { System.out.println("  ❌ Already friends!"); return; }

        connect(u1, u2);
        System.out.println("  ✅ " + u1 + " and " + u2 + " are now friends! 🤝");
    }

    static void viewFriends(Scanner sc) {
        System.out.print("\n  Enter user name: ");
        String name = sc.nextLine().trim();
        if (!graph.containsKey(name)) { System.out.println("  ❌ User not found!"); return; }

        Set<String> friends = graph.get(name);
        System.out.println("\n  👥 Friends of " + name + " (" + friends.size() + "):");
        if (friends.isEmpty()) System.out.println("  No friends yet.");
        else friends.forEach(f -> System.out.println("   • " + f));
    }

    // BFS - Friend Suggestions (Friends of Friends not already friends)
    static void friendSuggestions(Scanner sc) {
        System.out.print("\n  Enter user name for suggestions: ");
        String start = sc.nextLine().trim();
        if (!graph.containsKey(start)) { System.out.println("  ❌ User not found!"); return; }

        System.out.println("\n  🔍 BFS Friend Suggestion for: " + start);
        System.out.println("  (People connected through mutual friends)\n");

        Set<String> directFriends = graph.get(start);
        Map<String, Integer> suggestions = new LinkedHashMap<>();
        Set<String> visited = new HashSet<>(directFriends);
        visited.add(start);

        Queue<String> queue = new LinkedList<>(directFriends);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    suggestions.merge(neighbor, 1, Integer::sum);
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        if (suggestions.isEmpty()) {
            System.out.println("  No suggestions available right now.");
            return;
        }

        // Sort suggestions by mutual friend count
        suggestions.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  👤 %-15s (connected via %d mutual friend(s))%n",
                    e.getKey(), e.getValue()));
    }

    // DFS - Find all users reachable from given user
    static void dfsTraversal(Scanner sc) {
        System.out.print("\n  Enter starting user for DFS: ");
        String start = sc.nextLine().trim();
        if (!graph.containsKey(start)) { System.out.println("  ❌ User not found!"); return; }

        System.out.println("\n  🔎 DFS Traversal from: " + start);
        System.out.println("  (All users reachable in the network)\n");

        Set<String> visited = new LinkedHashSet<>();
        dfsRecursive(start, visited, 0);

        System.out.println("\n  Total reachable users: " + (visited.size() - 1));
    }

    // Recursive DFS
    static void dfsRecursive(String user, Set<String> visited, int depth) {
        visited.add(user);
        String indent = "  " + "    ".repeat(depth);
        System.out.println(indent + (depth == 0 ? "🏠 " : "➤ ") + user);

        for (String neighbor : graph.get(user)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, depth + 1);
            }
        }
    }

    static void displayNetwork() {
        System.out.println("\n━━━━━━━━━━  NETWORK GRAPH  ━━━━━━━━━━");
        System.out.println("  Users and their connections:\n");
        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            System.out.printf("  👤 %-12s → %s%n", entry.getKey(),
                    entry.getValue().isEmpty() ? "[No connections]" : String.join(", ", entry.getValue()));
        }
        System.out.println("\n  Total Users: " + graph.size());
        long totalEdges = graph.values().stream().mapToLong(Set::size).sum() / 2;
        System.out.println("  Total Friendships: " + totalEdges);
    }
}
