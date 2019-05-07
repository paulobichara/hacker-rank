package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Stream;

public class RoadsAndLibraries {

    private static class ConnectedComponent {
        private int id;
        private Map<Integer, Node> nodesMap;

        ConnectedComponent(int id) {
            this.id = id;
            nodesMap = new HashMap<>();
        }

        private static class NodeComparator implements Comparator<Node> {
            Map<Integer, Long> distances;

            NodeComparator(Map<Integer, Long> distances) {
                this.distances = distances;
            }

            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(distances.get(o1.value), distances.get(o2.value));
            }
        }

        private long getMinimumSpanningTreeEdges() {
            Map<Integer, Long> distances = new HashMap<>();
            nodesMap.values().forEach(node -> distances.put(node.value, Long.MAX_VALUE));
            distances.put(nodesMap.values().iterator().next().value, 0L);

            NodeComparator comparator = new NodeComparator(distances);
            PriorityQueue<Node> queue = new PriorityQueue<>(comparator);
            queue.addAll(nodesMap.values());

            long edgeCount = 0;

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                for (Node other : node.neighbours) {
                    long possibility = distances.get(node.value) + 1;
                    if (!node.equals(other) && queue.contains(other) && distances.get(other.value) > possibility) {
                        queue.remove(other);
                        distances.put(other.value, possibility);
                        queue.add(other);
                        edgeCount++;
                    }
                }
            }
            return edgeCount;
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
            component.nodesMap.put(value, this);
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

        List<ConnectedComponent> getConnectedComponents() {
            List<ConnectedComponent> connectedComponents = new ArrayList<>();
            int componentId = 0;
            ConnectedComponent current;
            Node node;

            while ((node = getNextUnvisitedNode()) != null) {
                componentId++;
                current = new ConnectedComponent(componentId);
                connectedComponents.add(current);
                node.explore(current);
            }

            return connectedComponents;
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

        long librariesCost = nodesQty * costLibrary;

        List<ConnectedComponent> components = graph.getConnectedComponents();
        long componentsCost = components.size() * costLibrary;
        for (ConnectedComponent component : components) {
            componentsCost = componentsCost + (component.getMinimumSpanningTreeEdges() * costRoad);
        }

        return Math.min(librariesCost, componentsCost);
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
