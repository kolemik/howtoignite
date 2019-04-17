package ru.ya.kolemik.ya05;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeUtilsTest2 {

    private static NodeImpl[] nodes;

    @BeforeClass
    public static void prepareTree() {
        int [] values = {4,8,10,12,14,20,22};
        nodes = new NodeImpl[values.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new NodeImpl(values[i]);
        }
        nodes[5].setLeft(nodes[1]);
        nodes[5].setRight(nodes[6]);
        nodes[1].setLeft(nodes[0]);
        nodes[1].setRight(nodes[3]);
        nodes[3].setLeft(nodes[2]);
        nodes[3].setRight(nodes[4]);
    }

    private NodeUtils utils;
    
    @Before
    public void setUp() {
        utils = new NodeUtils();
    }

    @Test
    public void test20() {
        assertEquals("20", utils.inorderSuccessor(nodes[4]).toString());
    }

    @Test
    public void testNull() {
        assertNull(utils.inorderSuccessor(nodes[6]));
    }
}
