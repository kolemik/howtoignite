package ru.ya.kolemik.ya05;

public class NodeImpl implements Node {
    private Node parent;
    private Node left;
    private Node right;
    private final int data;
    
    public NodeImpl(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeft(Node left) {
        if (left instanceof NodeImpl) {
            ((NodeImpl) left).setParent(this);
        }
        this.left = left;
    }

    public void setRight(Node right) {
        if (right instanceof NodeImpl) {
            ((NodeImpl) right).setParent(this);
        }
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(data);// (left==null?"":left) + "<" + "[" + data + "]" + ">" + (right==null?"":right); // TODO String.format
    }
}
