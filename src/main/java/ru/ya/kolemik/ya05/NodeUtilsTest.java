package ru.ya.kolemik.ya05;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeUtilsTest {

    private static NodeImpl[] nodes;
    private static int [] values;

    @BeforeClass
    public static void prepareTree() {
        values = new int [] {3,5,9,10,11};
        nodes = new NodeImpl[values.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new NodeImpl(values[i]);
        }
        nodes[1].setLeft(nodes[0]);
        nodes[1].setRight(nodes[3]);
        nodes[3].setLeft(nodes[2]);
        nodes[3].setRight(nodes[4]);
        /*
                5
              /   \
             3     10
                  /  \
                 9   11
         */
    }

    private NodeUtils utils;
    
    @Before
    public void setUp() {
        utils = new NodeUtils();
    }

    @Test
    public void test3() {
        assertEquals("5", utils.inorderSuccessor(nodes[0]).toString());
    }

    @Test
    public void test5() {
        assertEquals("9", utils.inorderSuccessor(nodes[1]).toString());
    }

    @Test
    public void test9() {
        assertEquals("10", utils.inorderSuccessor(nodes[2]).toString());
    }

    @Test
    public void test10() {
        assertEquals("11", utils.inorderSuccessor(nodes[3]).toString());
    }

    @Test
    public void test11() {
        assertNull(utils.inorderSuccessor(nodes[4]));
    }

    @Test
    public void test() {
        for (int i = 1; i < values.length; i++) {
            assertEquals("Fail to solve for: " + nodes[i -1].toString(), String.valueOf(values[i]), utils.inorderSuccessor(nodes[i -1]).toString());
        }
    }
}
