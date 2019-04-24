package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SwapNodes {

    private static class Node {
        int data;
        Node left;
        Node right;
        Node parent;

        Node(int data) {
            this.data = data;
        }

        void swap(int factor, int currentHeight) {
            if (currentHeight % factor == 0) {
                Node oldLeft = left;
                left = right;
                right = oldLeft;
            }
            if (left != null) {
                left.swap(factor, currentHeight + 1);
            }
            if (right != null) {
                right.swap(factor, currentHeight + 1);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                return data == ((Node)obj).data;
            }
            return false;
        }
    }

    private static class Tree {
        Node[] nodes;

        Tree(Node[] nodes) {
            this.nodes = nodes;
        }

        int[] swap(int factor) {
            nodes[0].swap(factor, 1);
            return listInOrder();
        }

        int[] listInOrder() {
            Map<Integer,Node> previousNodes = new HashMap<>();
            int[] inOrder = new int[nodes.length];
            int index = 0;

            for (Node current = nodes[0]; current != null;) {
                if (shouldProcessLeftChild(current, previousNodes.get(current.data))) {
                    previousNodes.put(current.data, current.left);
                    current = current.left;
                } else if (shouldProcessCurrent(current, previousNodes.get(current.data))) {
                    inOrder[index] = current.data;
                    index++;
                    previousNodes.put(current.data, current);
                } else if (shouldProcessRightChild(current, previousNodes.get(current.data))) {
                    previousNodes.put(current.data, current.right);
                    current = current.right;
                } else {
                    current = current.parent;
                }
            }
            return inOrder;
        }

        private boolean shouldProcessLeftChild(Node current, Node previous) {
            return previous == null && current.left != null;
        }

        private boolean shouldProcessCurrent(Node current, Node previous) {
            return (previous == null && current.left == null) ||
                (previous != null && previous.equals(current.left));
        }

        private boolean shouldProcessRightChild(Node current, Node previous) {
            return current.equals(previous) && current.right != null;
        }
    }

    /*
     * Complete the swapNodes function below.
     */
    private static int[][] swapNodes(int[][] indexes, int[] queries) {
        Tree tree = createTree(indexes);
        int[][] result = new int[queries.length][tree.nodes.length];
        int index = 0;
        for (int query : queries) {
            result[index] = tree.swap(query);
            index++;
        }
        return result;
    }

    private static Tree createTree(int[][] indexes) {
        Node[] nodes = new Node[indexes.length];
        for (int index = 0; index < indexes.length; index++) {
            if (nodes[index] == null) {
                nodes[index] = new Node(index + 1);
            }
            if (indexes[index][0] != -1) {
                int indexLeft = indexes[index][0] - 1;
                if (nodes[indexLeft] == null) {
                    nodes[indexLeft] = new Node(indexLeft + 1);
                }
                nodes[index].left = nodes[indexLeft];
                nodes[indexLeft].parent = nodes[index];
            }
            if (indexes[index][1] != -1) {
                int indexRight = indexes[index][1] - 1;
                if (nodes[indexRight] == null) {
                    nodes[indexRight] = new Node(indexRight + 1);
                }
                nodes[index].right = nodes[indexRight];
                nodes[indexRight].parent = nodes[index];
            }

        }
        return new Tree(nodes);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int n = Integer.parseInt(scanner.nextLine().trim());

        int[][] indexes = new int[n][2];

        for (int indexesRowItr = 0; indexesRowItr < n; indexesRowItr++) {
            String[] indexesRowItems = scanner.nextLine().split(" ");

            for (int indexesColumnItr = 0; indexesColumnItr < 2; indexesColumnItr++) {
                int indexesItem = Integer.parseInt(indexesRowItems[indexesColumnItr].trim());
                indexes[indexesRowItr][indexesColumnItr] = indexesItem;
            }
        }

        int queriesCount = Integer.parseInt(scanner.nextLine().trim());

        int[] queries = new int[queriesCount];

        for (int queriesItr = 0; queriesItr < queriesCount; queriesItr++) {
            int queriesItem = Integer.parseInt(scanner.nextLine().trim());
            queries[queriesItr] = queriesItem;
        }

        int[][] result = swapNodes(indexes, queries);

        for (int resultRowItr = 0; resultRowItr < result.length; resultRowItr++) {
            for (int resultColumnItr = 0; resultColumnItr < result[resultRowItr].length; resultColumnItr++) {
                bufferedWriter.write(String.valueOf(result[resultRowItr][resultColumnItr]));

                if (resultColumnItr != result[resultRowItr].length - 1) {
                    bufferedWriter.write(" ");
                }
            }

            if (resultRowItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }

}
