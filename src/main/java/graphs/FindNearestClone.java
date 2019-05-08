package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FindNearestClone {

    private static class NodeComparator implements Comparator<Node> {
        int[] distances;

        NodeComparator(int[] distances) {
            this.distances = distances;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return Long.compare(distances[o1.index], distances[o2.index]);
        }
    }

    private static class Node {
        List<Node> neighbours;
        long color;

        int index;

        Node(int index, long color) {
            neighbours = new ArrayList<>();
            this.index = index;
            this.color = color;
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

    static class Graph {

        Node[] nodes;
        Node start;

        Graph(int qtyNodes, long[] colors, long desiredColor) {
            nodes  = new Node[qtyNodes];
            for (int index = 0; index < qtyNodes; index++) {
                nodes[index] = new Node(index, colors[index]);
                if (colors[index] == desiredColor && start == null) {
                    start = nodes[index];
                }
            }
        }

        int getShortestPathSize() {
            if (start == null) {
                return -1;
            }

            return dijkstra(start);
        }

        private int dijkstra(Node start) {
            int[] distances = new int[nodes.length];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start.index] = 0;
            NodeComparator comparator = new NodeComparator(distances);

            PriorityQueue<Node> queue = new PriorityQueue<>(comparator);
            Collections.addAll(queue, nodes);

            int minDistance = Integer.MAX_VALUE;
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (!node.equals(start) && node.color == start.color) {
                    minDistance = Math.min(minDistance, distances[node.index]);
                }
                for (Node neighbour : node.neighbours) {
                    if (distances[neighbour.index] > distances[node.index] + 1) {
                        queue.remove(neighbour);
                        distances[neighbour.index] = distances[node.index] + 1;
                        comparator.distances = distances;
                        queue.add(neighbour);
                    }
                }
            }

            if (minDistance == Integer.MAX_VALUE) {
                return -1;
            }
            return minDistance;
        }
    }

    // Complete the findShortest function below.
    private static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        Graph graph = new Graph(graphNodes, ids, val);
        Node from, to;
        for (int index = 0; index < graphFrom.length; index++) {
            from = graph.nodes[graphFrom[index] - 1];
            to = graph.nodes[graphTo[index] - 1];
            from.addNeighbour(to);
            to.addNeighbour(from);
        }
        return graph.getShortestPathSize();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}
