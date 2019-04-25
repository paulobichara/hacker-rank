package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class TripleSum {

    static class Node {

        int key;
        int size;

        Node parent;
        Node leftChild;
        Node rightChild;

        Node(int key) {
            this.key = key;
            this.size = 1;
        }

        void setLeftChild(Node node) {
            leftChild = node;
            if (node != null) {
                node.parent = this;
            }
        }

        void setRightChild(Node node) {
            rightChild = node;
            if (node != null) {
                node.parent = this;
            }
        }

        void updateSize() {
            int sizeLeft = leftChild == null ? 0 : leftChild.size;
            int sizeRight = rightChild == null ? 0 : rightChild.size;
            size = sizeLeft + sizeRight + 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            return this.key == ((Node)obj).key;
        }
    }

    private interface SplayStrategy {
        boolean splayIfApplicable(Node node);

        default void updateGreatGrandparent(Node node, Node grandparent, Node greatGrandparent) {
            if (greatGrandparent != null) {
                if (grandparent.equals(greatGrandparent.leftChild)) {
                    greatGrandparent.setLeftChild(node);
                } else {
                    greatGrandparent.setRightChild(node);
                }
            } else {
                node.parent = null;
            }
        }
    }

    private static class ZigZigStrategy implements  SplayStrategy {

        private boolean isLeftCase(Node node) {
            return node.equals(node.parent.leftChild) && node.parent.equals(node.parent.parent.leftChild);
        }

        private boolean isRightCase(Node node) {
            return node.equals(node.parent.rightChild) && node.parent.equals(node.parent.parent.rightChild);
        }

        @Override
        public boolean splayIfApplicable(Node node) {
            Node parent = node.parent;
            Node grandparent = parent == null ? null : parent.parent;
            if (parent == null || grandparent == null) {
                return false;
            }

            Node greatGrandparent = grandparent.parent;

            if (isLeftCase(node)) {
                Node oldRight = node.rightChild;
                Node oldParentRight = parent.rightChild;
                node.setRightChild(parent);
                parent.setLeftChild(oldRight);
                parent.setRightChild(grandparent);
                grandparent.setLeftChild(oldParentRight);
            } else if (isRightCase(node)) {
                Node oldLeft = node.leftChild;
                Node oldParentLeft = parent.leftChild;
                node.setLeftChild(parent);
                parent.setRightChild(oldLeft);
                parent.setLeftChild(grandparent);
                grandparent.setRightChild(oldParentLeft);
            } else {
                return false;
            }

            updateGreatGrandparent(node, grandparent, greatGrandparent);

            grandparent.updateSize();
            parent.updateSize();
            return true;
        }
    }

    private static class ZigZagStrategy implements  SplayStrategy {

        private boolean isLessThanCase(Node node) {
            return node.equals(node.parent.rightChild) && node.parent.equals(node.parent.parent.leftChild);
        }

        private boolean isGreaterThanCase(Node node) {
            return node.equals(node.parent.leftChild) && node.parent.equals(node.parent.parent.rightChild);
        }

        @Override
        public boolean splayIfApplicable(Node node) {
            Node parent = node.parent;
            Node grandparent = parent == null ? null : parent.parent;
            if (parent == null || grandparent == null) {
                return false;
            }

            Node greatGrandparent = grandparent.parent;

            Node oldRight = node.rightChild;
            Node oldLeft = node.leftChild;

            if (isLessThanCase(node)) {
                node.setRightChild(grandparent);
                node.setLeftChild(parent);
                parent.setRightChild(oldLeft);
                grandparent.setLeftChild(oldRight);
            } else if (isGreaterThanCase(node)) {
                node.setLeftChild(grandparent);
                node.setRightChild(parent);
                parent.setLeftChild(oldRight);
                grandparent.setRightChild(oldLeft);
            } else {
                return false;
            }

            updateGreatGrandparent(node, grandparent, greatGrandparent);

            parent.updateSize();
            grandparent.updateSize();
            return true;
        }
    }

    private static class ZigStrategy implements  SplayStrategy {

        @Override
        public boolean splayIfApplicable(Node node) {
            Node parent = node.parent;
            if (parent == null || parent.parent != null) {
                return false;
            }

            node.parent = null;

            if (node.equals(parent.leftChild)) {
                Node oldRight = node.rightChild;
                node.setRightChild(parent);
                parent.setLeftChild(oldRight);
            } else {
                Node oldLeft = node.leftChild;
                node.setLeftChild(parent);
                parent.setRightChild(oldLeft);
            }

            parent.updateSize();
            return true;
        }
    }

    static class SplayTree {
        static final SplayStrategy[] STRATEGIES = new SplayStrategy[]{new ZigStrategy(), new ZigZagStrategy(), new ZigZigStrategy()};

        Node root = null;

        Node find(int key) {
            return find(key, true);
        }

        private Node find(int key, boolean mustSplay) {
            Node current = root;
            Node parent = null;
            while (current != null && current.key != key) {
                parent = current;
                if (key > current.key) {
                    current = current.rightChild;
                } else {
                    current = current.leftChild;
                }
            }

            Node node = current == null ? parent : current;
            if (mustSplay && node != null) {
                splay(node);
            }
            return node;
        }

        void insert(int key) {
            if (root == null) {
                root = new Node(key);
            } else {
                Node found = find(key, false);
                if (found.key != key) {
                    Node node = new Node(key);
                    if (found.key > key) {
                        found.setLeftChild(node);
                    } else {
                        found.setRightChild(node);
                    }
                    splay(node);
                }
            }
        }

        private void splay(Node node) {
            while (node.parent != null) {
                for (int index = 0; index < STRATEGIES.length && !STRATEGIES[index].splayIfApplicable(node); index++);
            }
            node.updateSize();
            root = node;
        }

    }

    static long triplets(int[] a, int[] b, int[] c) {
        SplayTree treeA = createTree(a);
        SplayTree treeC = createTree(c);
        Map<Integer, Boolean> computedMap = new HashMap<>();

        long numTriplets = 0;
        for (int index = 0; index < b.length; index++) {
            if (!computedMap.containsKey(b[index])) {
                computedMap.put(b[index], true);
                numTriplets += getPossibleCandidates(treeA, b[index]) * getPossibleCandidates(treeC, b[index]);
            }
        }
        return numTriplets;
    }

    private static int getPossibleCandidates(SplayTree tree, int upperBound) {
        Node found = tree.find(upperBound);
        if (found != null) {
            if (found.key > upperBound) {
                return found.leftChild == null ? 0 : found.leftChild.size;
            } else {
                return found.leftChild == null ? 1 : found.leftChild.size + 1;
            }
        }
        return 0;
    }

    private static SplayTree createTree(int[] array) {
        SplayTree tree = new SplayTree();
        IntStream.of(array).forEach(tree::insert);
        return tree;
    }

    private static class Triplet {
        int first;
        int second;
        int third;

        int occurrences;

        Triplet(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
            occurrences = 1;
        }

        boolean match(int a, int b, int c) {
            return first == a && second == b && third == c;
        }
    }

    // Complete the triplets function below.
    static long tripletsNaive(int[] a, int[] b, int[] c) {
        int[] distinctA = getDistinctSortedArray(a);
        int[] distinctB = getDistinctSortedArray(b);
        int[] distinctC = getDistinctSortedArray(c);

        Arrays.sort(distinctA);
        Arrays.sort(distinctB);
        Arrays.sort(distinctC);

        Map<Integer, List<Triplet>> tripletsMap = new HashMap<>();
        int countTriplets = 0;
        int indexMinAInB = getSearchIndex(distinctB, distinctA[0]);
        int indexMinCInB = getSearchIndex(distinctB, distinctC[0]);

        for (int indexB = Math.max(indexMinAInB, indexMinCInB); indexB < distinctB.length; indexB++) {
            final int valueB = distinctB[indexB];
            for (int indexA = 0; indexA < distinctA.length && distinctA[indexA] <= distinctB[indexB]; indexA++) {
                final int valueA = distinctA[indexA];
                for (int indexC = 0; indexC < distinctC.length && distinctC[indexC] <= distinctB[indexB]; indexC++) {
                    final int valueC = distinctC[indexC];
                    int key = distinctA[indexA] + distinctB[indexB] + distinctC[indexC];
                    if (!tripletsMap.containsKey(key)) {
                        tripletsMap.put(key, new ArrayList<>());
                    }
                    List<Triplet> triplets = tripletsMap.get(key);
                    Optional<Triplet> optional = triplets.stream().filter(triplet -> triplet.match(valueA, valueB, valueC)).findAny();
                    if (optional.isPresent()) {
                        optional.get().occurrences++;
                    } else {
                        triplets.add(new Triplet(valueA, valueB, valueC));
                        countTriplets++;
                    }
                }
            }
        }
        return countTriplets;
    }

    private static int[] getDistinctSortedArray(int[] array) {
        Set<Integer> set = new LinkedHashSet<>();
        IntStream.of(array).forEach(set::add);
        return set.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int getSearchIndex(int[] array, int key) {
        int resultIndex = Arrays.binarySearch(array, key);
        if (resultIndex < 0) {
            resultIndex = Math.abs(resultIndex + 1);
        }
        return resultIndex;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] lenaLenbLenc = scanner.nextLine().split(" ");

        int lena = Integer.parseInt(lenaLenbLenc[0]);

        int lenb = Integer.parseInt(lenaLenbLenc[1]);

        int lenc = Integer.parseInt(lenaLenbLenc[2]);

        int[] arra = new int[lena];

        String[] arraItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lena; i++) {
            int arraItem = Integer.parseInt(arraItems[i]);
            arra[i] = arraItem;
        }

        int[] arrb = new int[lenb];

        String[] arrbItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenb; i++) {
            int arrbItem = Integer.parseInt(arrbItems[i]);
            arrb[i] = arrbItem;
        }

        int[] arrc = new int[lenc];

        String[] arrcItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenc; i++) {
            int arrcItem = Integer.parseInt(arrcItems[i]);
            arrc[i] = arrcItem;
        }

        long ans = triplets(arra, arrb, arrc);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
