package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ConnectedCellInGrid {

    private static class ConnectedComponent {
        private int nonFilledSize;
        private int filledSize;
    }

    static class Node {
        List<Node> neighbours;
        int row;
        int column;
        boolean filled;
        boolean visited;

        Node(int row, int column) {
            this.row = row;
            this.column = column;
            filled = false;
            visited = false;
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
            return this.row == ((Node)obj).row && this.column == ((Node)obj).column;
        }
    }

    static class Graph {
        private Node[][] nodes;
        private int lastVisited;
        private List<Node> filledNodes;

        Graph(int[][] grid) {
            lastVisited = -1;
            nodes = new Node[grid.length][grid.length > 0 ? grid[0].length : 0];
            filledNodes = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
                for (int columnIndex = 0; columnIndex < grid[rowIndex].length; columnIndex++) {
                    Node current = getNode(rowIndex, columnIndex);
                    if (grid[rowIndex][columnIndex] == 1) {
                        current.filled = true;
                        filledNodes.add(current);
                        connectToAllNeighbours(grid, current, rowIndex, columnIndex);
                    }
                }
            }
        }

        Node getNode(int rowIndex, int columnIndex) {
            if (nodes[rowIndex][columnIndex] == null) {
                nodes[rowIndex][columnIndex] = new Node(rowIndex, columnIndex);
            }
            return nodes[rowIndex][columnIndex];
        }

        int getMaxRegion() {
            int maxRegion = 0;
            Node node;
            ConnectedComponent component;
            while ((node = getNextUnvisitedNode()) != null) {
                component = explore(node);
                if (component.filledSize > 1) {
                    maxRegion = Math.max(maxRegion, component.filledSize);
                } else {
                    maxRegion = Math.max(maxRegion, component.filledSize + component.nonFilledSize);
                }
            }

            return maxRegion;
        }

        private void connectToAllNeighbours(int[][] grid, Node current, int rowIndex, int columnIndex) {
            addTopNeighbours(grid, current, rowIndex, columnIndex);
            addMiddleNeighbours(grid, current, rowIndex, columnIndex);
            addBottomNeighbours(grid, current, rowIndex, columnIndex);
        }

        private void addTopNeighbours(int[][] grid, Node current, int rowIndex, int columnIndex) {
            int topIndex = rowIndex - 1;
            if (topIndex > 0) {
                int leftIndex = columnIndex - 1;
                int rightIndex = columnIndex + 1;
                connectNeighbours(current, getNode(topIndex, columnIndex));
                if (columnIndex > 0) {
                    connectNeighbours(current, getNode(topIndex, leftIndex));
                }
                if (rightIndex < grid[topIndex].length) {
                    connectNeighbours(current, getNode(topIndex, rightIndex));
                }
            }
        }

        private void addMiddleNeighbours(int[][] grid, Node current, int rowIndex, int columnIndex) {
            int leftIndex = columnIndex - 1;
            int rightIndex = columnIndex + 1;

            if (leftIndex > 0) {
                connectNeighbours(current, getNode(rowIndex, leftIndex));
            }
            if (rightIndex < grid[rowIndex].length) {
                connectNeighbours(current, getNode(rowIndex, rightIndex));
            }
        }

        private void addBottomNeighbours(int[][] grid, Node current, int rowIndex, int columnIndex) {
            int bottomIndex = rowIndex + 1;
            if (bottomIndex < grid.length) {
                int leftIndex = columnIndex - 1;
                int rightIndex = columnIndex + 1;
                connectNeighbours(current, getNode(bottomIndex, columnIndex));
                if (rightIndex < grid[bottomIndex].length) {
                    connectNeighbours(current, getNode(bottomIndex, rightIndex));
                }
                if (leftIndex > 0) {
                    connectNeighbours(current, getNode(bottomIndex, leftIndex));
                }
            }
        }

        private void connectNeighbours(Node neighbour1, Node neighbour2) {
            neighbour1.addNeighbour(neighbour2);
            neighbour2.addNeighbour(neighbour1);
        }

        private ConnectedComponent explore(Node node) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(node);

            node.visited = true;

            ConnectedComponent component = new ConnectedComponent();
            component.filledSize++;

            while (!nodeStack.isEmpty()) {
                Node current = nodeStack.pop();
                for (Node other : current.neighbours) {
                    if (!current.equals(other) && !other.visited) {
                        other.visited = true;
                        if (other.filled) {
                            component.filledSize++;
                            nodeStack.push(other);
                        } else {
                            component.nonFilledSize++;
                        }
                    }
                }
            }
            return component;
        }

        private Node getNextUnvisitedNode() {
            Node candidate;
            for (int index = lastVisited + 1; index < filledNodes.size(); index++) {
                lastVisited = index;
                candidate = filledNodes.get(index);
                if (!candidate.visited) {
                    return candidate;
                }
            }
            return null;
        }
    }

    // Complete the maxRegion function below.
    private static int maxRegion(int[][] grid) {
        Graph graph = new Graph(grid);
        return graph.getMaxRegion();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] grid = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] gridRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < m; j++) {
                int gridItem = Integer.parseInt(gridRowItems[j]);
                grid[i][j] = gridItem;
            }
        }

        int res = maxRegion(grid);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
