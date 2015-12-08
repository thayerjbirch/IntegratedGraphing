/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Graphs.Graph;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thayer
 */
public class GraphTest {
    
    public GraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        
    }

    /**
     * Test of buildKGraph method, of class Graph.
     */
    @Test
    public void testBuildKGraph() {
        System.out.println("buildKGraph");
        for(int order = 0; order < 12; order++){
            Graph instance = Graph.buildKGraph(0,0,order);
            int result = instance.size();
            assertEquals(Graph.maxEdges(order), result);
        }
    }

    /**
     * Test of addNode method, of class Graph.
     */
    @Test
    public void testAddNode_double_double() {
        System.out.println("addNode");
        double x = 0.0;
        double y = 0.0;
        Graph instance = new Graph(0,0);
        instance.addVertex(x, y);
    }

    /**
     * Test of addVertex method, of class Graph.
     */
    @Test
    public void testAddVertex_Point2D() {
        System.out.println("addVertex");
        Point2D p = new Point2D(0,0);
        Graph instance = new Graph(0,0);
        instance.addVertex(p);
    }

    /**
     * Test of addEdge method, of class Graph.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddEdge_3args() {
        System.out.println("addEdge");
        GraphVertex start = null;
        GraphVertex end = null;
        Color color = Color.ANTIQUEWHITE;
        Graph instance = new Graph(0,0);
        instance.addEdge(start, end, color);
    }

    /**
     * Test of addEdge method, of class Graph.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddEdge_GraphVertex_GraphVertex() {
        System.out.println("addEdge");
        GraphVertex start = null;
        GraphVertex end = null;
        Graph instance = new Graph(0,0);
        instance.addEdge(start, end);
    }

    /**
     * Test of findPoint2D method, of class Graph.
     */
    @Test
    public void testFindPoint2D() {
        System.out.println("findPoint2D");
        double rad = 0;
        Graph instance = new Graph(0,0);
        Point2D expResult = new Point2D(50,0);
        Point2D result = instance.findPoint2D(rad);
        assertEquals(expResult, result);
    }
    
}
