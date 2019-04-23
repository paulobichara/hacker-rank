package trees;

import java.util.Scanner;

public class LowestCommonAncestor {
    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    private static Node lca(Node root, int v1, int v2) {
        Node current = root;

        while (current != null) {
            if (current.data == v1 || current.data == v2
                    || (Math.max(v1, v2) > current.data && Math.min(v1, v2) < current.data)) {
                return current;
            } else if (v1 > current.data) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return null;
    }

    private static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        int v1 = scan.nextInt();
        int v2 = scan.nextInt();
        scan.close();
        Node ans = lca(root,v1,v2);
        System.out.println(ans.data);
    }
}
