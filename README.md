# 🌐 Social Network Friend Suggestion

A console-based Java application that models a social network using a Graph and provides friend suggestions using BFS and recursive DFS traversal.

---

## 📋 Features

- **Add User** — Add a new user (node) to the social network graph
- **Add Friendship** — Create a bidirectional connection (edge) between two users
- **View Friends** — List all direct friends of a given user
- **Friend Suggestions (BFS)** — Suggest people you may know using Breadth-First Search (friends of friends, ranked by mutual connections)
- **All Connected Users (DFS)** — Recursively traverse and display all users reachable from a given user using Depth-First Search
- **Display Network** — Show the full adjacency list of the entire network
- **Preloaded Network** — 7 sample users with connections are loaded on startup

---

## 🧠 Concepts Used

| Concept | Usage |
|--------|-------|
| Graph (Adjacency List) | `HashMap<String, Set<String>>` represents users and their connections |
| BFS (Breadth-First Search) | Friend suggestion — explores level by level using a Queue |
| Recursive DFS (Depth-First Search) | Traverses all reachable users from a starting node using recursion |
| HashMap | Stores the graph and suggestion counts |
| LinkedHashSet | Maintains insertion order for each user's friend list |
| Queue | Used in BFS traversal |

---

## 📁 File Structure

```
4_SocialNetwork/
├── src/
│   └── SocialNetwork.java   ← Main source file
├── out/                     ← Compiled .class files (auto-generated)
├── run.sh                   ← Run script for Linux/Mac
└── run.bat                  ← Run script for Windows
```

---

## ▶️ How to Run

### Linux / Mac
```bash
cd 4_SocialNetwork
bash run.sh
```

### Windows
```
cd 4_SocialNetwork
run.bat
```

### Manual (Any OS)
```bash
javac -d out src/SocialNetwork.java
java -cp out SocialNetwork
```

---

## 🖥️ Sample Output

```
╔══════════════════════════════════════════╗
║  🌐  SOCIAL NETWORK FRIEND SUGGESTION  🌐 ║
╚══════════════════════════════════════════╝

  Enter choice: 4   ← Friend Suggestions for Arjun

  🔍 BFS Friend Suggestion for: Arjun
  (People connected through mutual friends)

  👤 Sneha          (connected via 1 mutual friend(s))
  👤 Karthik        (connected via 1 mutual friend(s))
  👤 Divya          (connected via 1 mutual friend(s))

  Enter choice: 5   ← DFS from Arjun

  🔎 DFS Traversal from: Arjun
  🏠 Arjun
      ➤ Priya
          ➤ Sneha
              ➤ Vikram
                  ➤ Karthik
          ➤ Karthik (already visited)
      ➤ Rohit
          ➤ Divya
```

---

## 🔗 Preloaded Network

```
Arjun   ── Priya, Rohit
Priya   ── Arjun, Sneha, Karthik
Rohit   ── Arjun, Divya
Sneha   ── Priya, Vikram
Karthik ── Priya, Vikram
Divya   ── Rohit
Vikram  ── Sneha, Karthik
```

---

## ⚙️ Requirements

- Java JDK 17 or above
- Terminal / Command Prompt

---

## 📌 Notes

- All friendships are **bidirectional** (undirected graph)
- BFS suggestion ranking is based on number of mutual connections
- DFS is implemented **recursively** with depth-based indentation for visual clarity
- Data is stored in memory only (resets on exit)
