package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Stream;

public class RoadsAndLibraries {

    private static class ConnectedComponent {
        private int size;
    }

    private static class Node {
        List<Node> neighbours;
        int value;
        boolean visited;

        Node(int value) {
            this.value = value;

            neighbours = new ArrayList<>();
            visited = false;
        }

        void addNeighbour(Node node) {
            neighbours.add(node);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            return this.value == ((Node)obj).value;
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

        private ConnectedComponent explore(Node node) {
            Stack<Node> stack = new Stack<>();
            stack.push(node);

            node.visited = true;

            ConnectedComponent component = new ConnectedComponent();
            component.size++;

            while (!stack.isEmpty()) {
                Node current = stack.pop();
                for (Node other : current.neighbours) {
                    if (!current.equals(other) && !other.visited) {
                        component.size++;
                        other.visited = true;
                        stack.push(other);
                    }
                }
            }
            return component;
        }

        long getMinimalCost(int costRoad, int costLibrary) {
            Node node;
            long maxCost = nodes.length * costLibrary;
            long currentCost = 0;

            while ((node = getNextUnvisitedNode()) != null && currentCost < maxCost) {
                currentCost = currentCost + costLibrary + (explore(node).size - 1) * costRoad;
            }

            return Math.min(maxCost, currentCost);
        }

        private Node getNextUnvisitedNode() {
            return Stream.of(nodes).filter(node -> !node.visited).findFirst().orElse(null);
        }
    }

    // Complete the roadsAndLibraries function below.
    private static long roadsAndLibraries(int nodesQty, int costLibrary, int costRoad, int[][] roads) {
        Graph graph = new Graph(nodesQty);
        Node first, second;
        for (int[] road : roads) {
            first = graph.getNode(road[0] - 1);
            second = graph.getNode(road[1] - 1);
            first.addNeighbour(second);
            second.addNeighbour(first);
        }

        return graph.getMinimalCost(costRoad, costLibrary);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nmC_libC_road = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nmC_libC_road[0]);

            int m = Integer.parseInt(nmC_libC_road[1]);

            int c_lib = Integer.parseInt(nmC_libC_road[2]);

            int c_road = Integer.parseInt(nmC_libC_road[3]);

            int[][] cities = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] citiesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int citiesItem = Integer.parseInt(citiesRowItems[j]);
                    cities[i][j] = citiesItem;
                }
            }

            long result = roadsAndLibraries(n, c_lib, c_road, cities);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}
