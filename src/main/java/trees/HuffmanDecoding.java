package trees;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class Solution {

        static abstract class Node implements Comparable<Node> {
            int frequency; // the frequency of this tree
            char data;
            Node left, right;
            Node(int freq) {
                frequency = freq;
            }

            // compares on the frequency
            public int compareTo(Node tree) {
                return frequency - tree.frequency;
            }
        }

        static class HuffmanLeaf extends Node {


            HuffmanLeaf(int freq, char val) {
                super(freq);
                data = val;
            }
        }

        static class HuffmanNode extends Node {

            HuffmanNode(Node l, Node r) {
                super(l.frequency + r.frequency);
                left = l;
                right = r;
            }

        }

        static class Decoding {

            static final char NULL = '\u0000';
            static final char ONE = '1';

            void decode(String s, Node root) {
                StringBuilder builder = new StringBuilder();

                Node current = root;
                for (int index = 0; index < s.length();) {
                    char character = s.charAt(index);
                    if (current.data == NULL) {
                        if (character == ONE) {
                            current = current.right;
                        } else {
                            current = current.left;
                        }
                        index++;
                    } else {
                        builder.append(current.data);
                        current = root;
                    }
                }
                builder.append(current.data);
                System.out.println(builder.toString());
            }
        }

        // input is an array of frequencies, indexed by character code
        private static Node buildTree(int[] charFreqs) {

            PriorityQueue<Node> trees = new PriorityQueue<>();
            // initially, we have a forest of leaves
            // one for each non-empty character
            for (int i = 0; i < charFreqs.length; i++)
                if (charFreqs[i] > 0)
                    trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));

            assert trees.size() > 0;

            // loop until there is only one tree left
            while (trees.size() > 1) {
                // two trees with least frequency
                Node a = trees.poll();
                Node b = trees.poll();

                // put into new node and re-insert into queue
                trees.offer(new HuffmanNode(a, b));
            }

            return trees.poll();
        }

        static Map<Character,String> mapA=new HashMap<>();

        static void printCodes(Node tree, StringBuffer prefix) {

            assert tree != null;

            if (tree instanceof HuffmanLeaf) {
                HuffmanLeaf leaf = (HuffmanLeaf)tree;

                // print out character, frequency, and code for this leaf (which is just the prefix)
                //System.out.println(leaf.data + "\t" + leaf.frequency + "\t" + prefix);
                mapA.put(leaf.data,prefix.toString());

            } else if (tree instanceof HuffmanNode) {
                HuffmanNode node = (HuffmanNode)tree;

                // traverse left
                prefix.append('0');
                printCodes(node.left, prefix);
                prefix.deleteCharAt(prefix.length()-1);

                // traverse right
                prefix.append('1');
                printCodes(node.right, prefix);
                prefix.deleteCharAt(prefix.length()-1);
            }
        }

        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            String test= input.next();

            // we will assume that all our characters will have
            // code less than 256, for simplicity
            int[] charFreqs = new int[256];

            // read each character and record the frequencies
            for (char c : test.toCharArray())
                charFreqs[c]++;

            // build tree
            Node tree = buildTree(charFreqs);

            // print out results
            printCodes(tree, new StringBuffer());
            StringBuffer s = new StringBuffer();

            for(int i = 0; i < test.length(); i++) {
                char c = test.charAt(i);
                s.append(mapA.get(c));
            }

            //System.out.println(s);
            Decoding d = new Decoding();
            d.decode(s.toString(), tree);

    }
}

