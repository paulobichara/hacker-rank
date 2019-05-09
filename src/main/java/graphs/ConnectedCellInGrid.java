package graphs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ConnectedCellInGrid {

    private static class Region {
        private int size;

        Region() {
            size = 1;
        }
    }

    static class Node {
        List<Node> neighbours;
        List<Node> filledNeighbours;
        int row;
        int column;
        boolean filled;
        boolean visited;
        Region region;

        Node(int row, int column, boolean filled) {
            this.row = row;
            this.column = column;
            this.filled = filled;
            visited = false;
            neighbours = new ArrayList<>();
            filledNeighbours = new ArrayList<>();
            region = null;
        }

        void addNeighbour(Node node) {
            neighbours.add(node);
            if (node.filled) {
                filledNeighbours.add(node);
            }
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
        int[][] grid;

        Graph(int[][] grid) {
            lastVisited = -1;
            nodes = new Node[grid.length][grid.length > 0 ? grid[0].length : 0];
            filledNodes = new ArrayList<>();
            this.grid = grid;
            for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
                for (int columnIndex = 0; columnIndex < grid[rowIndex].length; columnIndex++) {
                    Node current = getNode(rowIndex, columnIndex);
                    if (grid[rowIndex][columnIndex] == 1) {
                        filledNodes.add(current);
                        connectToAllNeighbours(grid, current, rowIndex, columnIndex);
                    }
                }
            }
        }

        Node getNode(int rowIndex, int columnIndex) {
            if (nodes[rowIndex][columnIndex] == null) {
                nodes[rowIndex][columnIndex] = new Node(rowIndex, columnIndex, grid[rowIndex][columnIndex] == 1);
            }
            return nodes[rowIndex][columnIndex];
        }

        int getMaxRegion() {
            int maxRegion = 0;
            Node node;
            while ((node = getNextUnvisitedNode()) != null) {
                maxRegion = Math.max(explore(node), maxRegion);
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

        private int explore(Node node) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(node);
            node.visited = true;

            node.region = node.region == null ? new Region() : node.region;

            boolean adjacentOnesOnly = false;

            while (!nodeStack.isEmpty()) {
                Node current = nodeStack.pop();
                if (adjacentOnesOnly || current.filledNeighbours.size() > 0) {
                    adjacentOnesOnly = true;
                    for (Node neighbour : current.filledNeighbours) {
                        if (!current.equals(neighbour) && !neighbour.visited) {
                            current.region.size++;
                            neighbour.region = current.region;
                            neighbour.visited = true;
                            nodeStack.push(neighbour);
                        }
                    }
                } else {
                    for (Node other : current.neighbours) {
                        if (!current.equals(other) && !other.visited) {
                            other.visited = true;
                            if (other.region == null) {
                                current.region.size++;
                                other.region = current.region;
                            } else {
                                Region region = current.region;
                                current.region = other.region;
                                current.region.size += region.size;
                            }
                        } else {
                            Region region = current.region;
                            current.region = other.region;
                            current.region.size += region.size;
                        }
                    }
                }

            }
            return node.region.size;
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
