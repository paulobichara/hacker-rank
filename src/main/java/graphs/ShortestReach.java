package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ShortestReach {

    private static class Node {
        List<Node> neighbours;
        int index;

        Node(int index) {
            this.index = index;
            neighbours = new ArrayList<>();
        }

        void addNeighbour(Node node) {
            neighbours.add(node);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            return this.index == ((Node)obj).index;
        }
    }

    private static class Graph {
        private Node[] nodes;

        Graph(int qtyNodes) {
            nodes = new Node[qtyNodes];
            for (int index = 0; index < qtyNodes; index++) {
                nodes[index] = new Node(index);
            }
        }

        Node getNode(int index) {
            if (nodes[index] == null) {
                nodes[index] = new Node(index);
            }
            return nodes[index];
        }

        long[] minimalDistance(int startIndex) {
            long[] distances = new long[nodes.length];
            Arrays.fill(distances, -1);
            distances[startIndex] = 0;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(startIndex);
            while (!queue.isEmpty()) {
                Node current = nodes[queue.poll()];
                for (Node neighbour : current.neighbours) {
                    if (distances[neighbour.index] == -1) {
                        queue.add(neighbour.index);
                        distances[neighbour.index] = distances[current.index] + 6;
                    }
                }
            }
            return distances;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int queriesQty = scanner.nextInt();
            for (int queryIndex = 0; queryIndex < queriesQty; queryIndex++) {
                int qtyNodes = scanner.nextInt();
                Graph graph = new Graph(qtyNodes);

                int qtyEdges = scanner.nextInt();
                for (int edgeIndex = 0; edgeIndex < qtyEdges; edgeIndex++) {
                    Node first = graph.getNode(scanner.nextInt() - 1);
                    Node second = graph.getNode(scanner.nextInt() - 1);
                    first.addNeighbour(second);
                    second.addNeighbour(first);
                }

                int startIndex = scanner.nextInt() - 1;
                long[] distances = graph.minimalDistance(startIndex);
                for (int index = 0; index < distances.length; index++) {
                    if (index != startIndex) {
                        System.out.print(distances[index] + " ");
                    }
                }
                System.out.println();
            }
        }
    }

}
