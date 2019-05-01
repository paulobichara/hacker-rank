package linkedlists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CycleDetection {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    boolean hasCycle(Node head) {
        Map<Integer, List<Node>> visitedMap = new HashMap<>();
        for (Node current = head; current != null; current = current.next) {
            if (visitedMap.containsKey(current.data) && visitedMap.get(current.data).contains(current)) {
                return true;
            } else {
                if (!visitedMap.containsKey(current.data)) {
                    visitedMap.put(current.data, new ArrayList<>());
                }
                visitedMap.get(current.data).add(current);
            }
        }
        return false;
    }
}
