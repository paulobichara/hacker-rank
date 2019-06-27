package trees;

import java.util.Stack;

public class SelfBalancingTree {

    static class Node {
    	int val;	//Value
    	int ht;		//Height
    	Node left;	//Left child
    	Node right;	//Right child
	}

    private static Node insert(Node root, int val)
    {
        if (root == null) {
            return createNode(null, val);
        }

        Stack<Node> nodeStack = find(root, val);
        Node current = nodeStack.pop();
        if (current.val == val) {
            return root;
        }

        createNode(current, val);

        while (!nodeStack.isEmpty()) {
            current = nodeStack.pop();
            int heightLeft = current.left == null ? -1 : current.left.ht;
            int heightRight = current.right == null ? -1 : current.right.ht;
            int balanceFactor = Math.abs(heightLeft - heightRight);
            if (balanceFactor > 1) {
                if (!nodeStack.isEmpty()) {
                    Node parent = nodeStack.peek();
                    if (parent.val < current.val) {
                        parent.right = reBalance(current);
                    } else {
                        parent.left = reBalance(current);
                    }
                } else {
                    root = reBalance(current);
                }

            } else {
                updateHeight(current);
            }
        }

        return root;
    }

    private static Node reBalance(Node node) {
        Node newMiddle;
        if (isLeftHigher(node) && isLeftHigher(node.left)) {
            newMiddle = node.left;
            node.left = newMiddle.right;
            newMiddle.right = node;
            updateHeight(newMiddle.right);
        } else if (!isLeftHigher(node) && !isLeftHigher(node.right)) {
            newMiddle = node.right;
            node.right = newMiddle.left;
            newMiddle.left = node;
            updateHeight(newMiddle.left);
        } else if (isLeftHigher(node) && !isLeftHigher(node.left)) {
            Node newLeft = node.left;
            newMiddle = newLeft.right;
            newLeft.right = newMiddle.left;
            newMiddle.left = newLeft;
            node.left = newMiddle.right;
            newMiddle.right = node;
            updateHeight(newMiddle.right);
            updateHeight(newMiddle.left);
        } else {
            Node newRight = node.right;
            newMiddle = newRight.left;
            newRight.left = newMiddle.right;
            newMiddle.right = newRight;
            node.right = newMiddle.left;
            newMiddle.left = node;
            updateHeight(newMiddle.right);
            updateHeight(newMiddle.left);
        }

        updateHeight(newMiddle);

        return newMiddle;
    }

    private static boolean isLeftHigher(Node node) {
        return (node.left != null && (node.right == null || node.left.ht > node.right.ht));
    }

    private static Node createNode(Node parent, int val) {
        Node node = new Node();
        node.val = val;
        node.ht = 0;

        if (parent != null) {
            if (val > parent.val) {
                parent.right = node;
            } else {
                parent.left = node;
            }
            updateHeight(parent);
        }

        return node;
    }

    private static void updateHeight(Node node) {
        node.ht = Math.max(
            node.left == null ? -1 : node.left.ht,
            node.right == null ? -1 : node.right.ht
        ) + 1;
    }

    private static Stack<Node> find(Node root,int val) {
        Node node = root;
        Stack<Node> nodeStack = new Stack<>();
        while ((val > node.val && node.right != null) || (val < node.val && node.right != null)) {
            nodeStack.push(node);

            if (node.val == val) {
                return nodeStack;
            }

            if (val > node.val) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        nodeStack.push(node);
        return nodeStack;
    }

    public static void main(String[] args) {
        Node root = insert(null, 3);
        root = insert(root, 2);
        root = insert(root, 4);
        root = insert(root, 5);
        insert(root, 6);
    }

}
