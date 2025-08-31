import java.util.*;

public class LRUApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter cache capacity:");
        int capacity = sc.nextInt();
        sc.nextLine(); 

        LRUCache cache = new LRUCache(capacity);

        System.out.println("\n🧠 LRU Cache Console");
        System.out.println("Commands:");
        System.out.println("  put <key> <value>");
        System.out.println("  get <key>");
        System.out.println("  display");
        System.out.println("  exit");
        System.out.println("--------------------");

        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            String[] parts = line.split("\s+");

            if (parts.length == 0) continue;

            String command = parts[0].toLowerCase();

            switch (command) {
                case "put":
                    if (parts.length == 3) {
                        int key = Integer.parseInt(parts[1]);
                        int value = Integer.parseInt(parts[2]);
                        cache.put(key, value);
                        System.out.println("✅ Put done");
                    } else {
                        System.out.println("❌ Usage: put <key> <value>");
                    }
                    break;

                case "get":
                    if (parts.length == 2) {
                        int key = Integer.parseInt(parts[1]);
                        int value = cache.get(key);
                        System.out.println("🔍 Value: " + value);
                    } else {
                        System.out.println("❌ Usage: get <key>");
                    }
                    break;

                case "display":
                    System.out.println("📦 Cache Contents:");
                    cache.display();
                    break;

                case "exit":
                    System.out.println("👋 Exiting...");
                    return;

                default:
                    System.out.println("❓ Unknown command. Try: put, get, display, exit");
            }
        }
    }
}

class LRUCache {
    class Node {
        int key, value;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> mp;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        mp = new HashMap<>();

        head = new Node(0, 0); 
        tail = new Node(0, 0); 

        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertAtFront(Node node) {
        Node temp = head.next;
        node.next = temp;
        node .prev = head;
        temp.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!mp.containsKey(key))
            return -1;

        Node node = mp.get(key);
        remove(node);
        insertAtFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (mp.containsKey(key)) {
            remove(mp.get(key));
            mp.remove(key);
        }

        if (mp.size() == capacity) {
            mp.remove(tail.prev.key);
            remove(tail.prev);
        }

        Node newNode = new Node(key, value);
        mp.put(key, newNode);
        insertAtFront(newNode);
    }

    public void display() {
        System.out.print("Cache: ");
        if(head == null)System.out.println("Empty");
        else {
        Node current = head.next;
        System.out.print("Most Recently Used -> ");
        while (current != tail) {
            System.out.print("[" + current.key + ":" + current.value + "] ");
            current = current.next;
        }
        System.out.print("-> Least Recently Used");
        System.out.println();
    }
    }
}
