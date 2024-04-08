package BinTreeJ;

import java.io.*;

class Node {
    int data;
    Node left, right, father;
}

public class BinTree {
    private Node root;

    public BinTree() {
        root = null;
    }
	
    public Node allocateNode(int data) {
        Node newNode = new Node();
        newNode.data = data;
        newNode.left = newNode.right = newNode.father = null;
        return newNode;
    }	

    public void insert(int data) {
        root = insert(data, root);
    }

    private Node insert(int data, Node root) {
        if (root == null) {
            root = allocateNode(data);
        } else if (data <= root.data) {
            root.left = insert(data, root.left);
        } else {
            root.right = insert(data, root.right);
        }
        return root;
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node root) {
        if (root == null) return;
        preOrder(root.left);
        preOrder(root.right);
    }

    public void central() {
        central(root);
    }

    private void central(Node root) {
        if (root == null) return;
        central(root.left);
        central(root.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
    }

    public void remove(int data) {
        root = remove(data, root);
    }

    private Node remove (int data, Node root) {
        if (root == null) return root;
        if (data < root.data) {
            root.left = remove(data, root.left);
        } else if (data > root.data) {
            root.right = remove(data, root.right);
        } else {
            if (root.left == null) {
                Node temp = root.right;
                root = null;
                return temp;
            } else if (root.right == null) {
                Node temp = root.left;
                root = null;
                return temp;
            }
            Node temp = searchMin(root.right);
            root.data = temp.data;
            root.right = remove(temp.data, root.right);
        }
        return root;
    }

    public Node search(int data) {
        return search(data, root);
    }

    private Node search(int data, Node root) {
        if (root == null || root.data == data) return root;
        if (data < root.data) return search(data, root.left);
        return search(data, root.right);
    }

    public int search() {
        Node minNodo = searchMin(root);
        if (minNodo != null) {
            return minNodo.data;
        }
        return -1;
    }

    private Node searchMin(Node root) {
        if (root == null) return null;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private void writePreOrderDot(Node root, BufferedWriter arqSaida) throws IOException {
        if (root != null) {
            if (root.left != null)
                arqSaida.write(root.data + " -> " + root.left.data + ";\n");
            if (root.right != null)
                arqSaida.write(root.data + " -> " + root.right.data + ";\n");
            writePreOrderDot(root.left, arqSaida);
            writePreOrderDot(root.right, arqSaida);
        }
    }

    public void generateDotFile(String filename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));

            out.write("digraph BinTree {\n");
            out.write("node [shape=circle, style=filled, color=black, fillcolor=\"#0096c7\"];\n");
            out.write("edge [color=black];\n");
            writePreOrderDot(root, out);
            out.write("}\n");

            out.close();
        } catch (IOException e) {
            System.out.println("Error opening file");
            e.printStackTrace();
        }
    }
}
