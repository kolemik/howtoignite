package ru.ya.kolemik.ya05;

public class NodeUtils {

    public Node inorderSuccessor(Node node) {
        if (node.getRight() != null) {
            return lookupForLeftDown(node.getRight());
        } else {
            return lookupForLeftUp(node);
        }
    }

    private Node lookupForLeftDown(Node node) {
        Node left;
        while (null != (left = node.getLeft())) {
            node = left;
        }
        return node;
    }

    private Node lookupForLeftUp(Node node) {
        Node parent = node.getParent();
        if (parent == null) {
            return null;
        }
        if (node.equals(parent.getLeft())) {
            return parent;
        } else {
            return lookupForLeftUp(parent);
        }
    }
}
