package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class RoadsAndLibraries {

    private static class ConnectedComponent {
        private int id;
        private int size;

        ConnectedComponent(int id) {
            this.id = id;
            size = 0;
        }
    }

    private static class Node {
        List<Node> neighbours;
        Integer componentId;
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

        void explore(ConnectedComponent component) {
            visited = true;
            component.size++;
            for (Node neighbour : neighbours) {
                if (!neighbour.visited) {
                    neighbour.visited = true;
                    neighbour.componentId = component.id;
                    neighbour.explore(component);
                }
            }
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

        long getMinimalCost(int costRoad, int costLibrary) {
            int componentId = 0;
            ConnectedComponent current;
            Node node;
            long maxCost = nodes.length * costLibrary;
            long currentCost = 0;

            while ((node = getNextUnvisitedNode()) != null && currentCost < maxCost) {
                componentId++;
                current = new ConnectedComponent(componentId);
                node.explore(current);
                currentCost = currentCost + costLibrary + (current.size - 1) * costRoad;
            }

            return Math.min(maxCost, currentCost);
        }

        private Node getNextUnvisitedNode() {
            Optional<Node> optional = Stream.of(nodes).filter(node -> !node.visited).findFirst();
            return optional.isPresent() ? optional.get() : null;
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
