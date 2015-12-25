/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Graphs.Graph;
import java.util.ArrayList;
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

    /**
     * Test of getVertexSet method, of class Graph.
     */
    @Test
    public void testGetVertexSet() {
        System.out.println("getVertexSet");
        Graph instance = new Graph();
        ArrayList<GraphVertex> expResult = null;
        ArrayList<GraphVertex> result = instance.getVertexSet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdgeSet method, of class Graph.
     */
    @Test
    public void testGetEdgeSet() {
        System.out.println("getEdgeSet");
        Graph instance = new Graph();
        ArrayList<GraphEdge> expResult = null;
        ArrayList<GraphEdge> result = instance.getEdgeSet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdges method, of class Graph.
     */
    @Test
    public void testGetEdges() {
        System.out.println("getEdges");
        Graph instance = new Graph();
        ArrayList<GraphEdge> expResult = null;
        ArrayList<GraphEdge> result = instance.getEdgeList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefaultRadius method, of class Graph.
     */
    @Test
    public void testGetDefaultRadius() {
        System.out.println("getDefaultRadius");
        Graph instance = new Graph();
        double expResult = 0.0;
        double result = instance.getDefaultRadius();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reorderVertexs method, of class Graph.
     */
    @Test
    public void testReorderVertexs_0args() {
        System.out.println("reorderVertexs");
        Graph instance = new Graph();
        instance.reorderVertexs();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reorderVertexs method, of class Graph.
     */
    @Test
    public void testReorderVertexs_double() {
        System.out.println("reorderVertexs");
        double radius = 0.0;
        Graph instance = new Graph();
        instance.reorderVertexs(radius);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildKGraph method, of class Graph.
     */
    @Test
    public void testBuildKGraph_3args() {
        System.out.println("buildKGraph");
        double x = 0.0;
        double y = 0.0;
        int order = 0;
        Graph expResult = null;
        Graph result = Graph.buildKGraph(x, y, order);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildKGraph method, of class Graph.
     */
    @Test
    public void testBuildKGraph_int() {
        System.out.println("buildKGraph");
        int order = 0;
        Graph expResult = null;
        Graph result = Graph.buildKGraph(order);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVertex method, of class Graph.
     */
    @Test
    public void testAddVertex_double_double() {
        System.out.println("addVertex");
        double x = 0.0;
        double y = 0.0;
        Graph instance = new Graph();
        GraphVertex expResult = null;
        GraphVertex result = instance.addVertex(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawEdge method, of class Graph.
     */
    @Test
    public void testDrawEdge_GraphVertex_GraphVertex() {
        System.out.println("drawEdge");
        GraphVertex start = null;
        GraphVertex end = null;
        Graph instance = new Graph();
        GraphEdge expResult = null;
        GraphEdge result = instance.drawEdge(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawEdge method, of class Graph.
     */
    @Test
    public void testDrawEdge_GraphEdge() {
        System.out.println("drawEdge");
        GraphEdge e = null;
        Graph instance = new Graph();
        instance.drawEdge(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteEdge method, of class Graph.
     */
    @Test
    public void testDeleteEdge() {
        System.out.println("deleteEdge");
        GraphEdge e = null;
        Graph instance = new Graph();
        boolean expResult = false;
        boolean result = instance.deleteEdge(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("removeEdge");
        GraphEdge e = null;
        Graph instance = new Graph();
        boolean expResult = false;
        boolean result = instance.removeEdge(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of order method, of class Graph.
     */
    @Test
    public void testOrder() {
        System.out.println("order");
        Graph instance = new Graph();
        int expResult = 0;
        int result = instance.order();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class Graph.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Graph instance = new Graph();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of maxEdges method, of class Graph.
     */
    @Test
    public void testMaxEdges_int() {
        System.out.println("maxEdges");
        int order = 0;
        int expResult = 0;
        int result = Graph.maxEdges(order);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of maxEdges method, of class Graph.
     */
    @Test
    public void testMaxEdges_Graph() {
        System.out.println("maxEdges");
        Graph g = null;
        int expResult = 0;
        int result = Graph.maxEdges(g);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complement method, of class Graph.
     */
    @Test
    public void testComplement() {
        System.out.println("complement");
        Graph instance = new Graph();
        instance.complement();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of elementOf method, of class Graph.
     */
    @Test
    public void testElementOf_GraphVertex() {
        System.out.println("elementOf");
        GraphVertex v = null;
        Graph instance = new Graph();
        boolean expResult = false;
        boolean result = instance.elementOf(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of elementOf method, of class Graph.
     */
    @Test
    public void testElementOf_GraphEdge() {
        System.out.println("elementOf");
        GraphEdge e = null;
        Graph instance = new Graph();
        boolean expResult = false;
        boolean result = instance.elementOf(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEdge method, of class Graph.
     */
    @Test
    public void testFindEdge_GraphVertex_GraphVertex() {
        System.out.println("findEdge");
        GraphVertex u = null;
        GraphVertex v = null;
        Graph instance = new Graph();
        GraphEdge expResult = null;
        GraphEdge result = instance.findEdge(u, v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEdge method, of class Graph.
     */
    @Test
    public void testFindEdge_GraphEdge() {
        System.out.println("findEdge");
        GraphEdge e = null;
        Graph instance = new Graph();
        GraphEdge expResult = null;
        GraphEdge result = instance.findEdge(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recenterCircle method, of class Graph.
     */
    @Test
    public void testRecenterCircle() {
        System.out.println("recenterCircle");
        Graph instance = new Graph();
        instance.recenterCircle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of translate method, of class Graph.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double x = 0.0;
        double y = 0.0;
        Graph instance = new Graph();
        instance.translate(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toFront method, of class Graph.
     */
    @Test
    public void testToFront() {
        System.out.println("toFront");
        Graph instance = new Graph();
        instance.toFront();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verticesToFront method, of class Graph.
     */
    @Test
    public void testVerticesToFront() {
        System.out.println("verticesToFront");
        Graph instance = new Graph();
        instance.verticesToFront();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("removeVertex");
        GraphVertex v = null;
        Graph instance = new Graph();
        boolean expResult = false;
        boolean result = instance.removeVertex(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertexSetChanged method, of class Graph.
     */
    @Test
    public void testVertexSetChanged() {
        System.out.println("vertexSetChanged");
        Graph instance = new Graph();
        instance.vertexSetChanged();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeSetChanged method, of class Graph.
     */
    @Test
    public void testEdgeSetChanged() {
        System.out.println("edgeSetChanged");
        Graph instance = new Graph();
        instance.edgeSetChanged();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDegreeSequence method, of class Graph.
     */
    @Test
    public void testGetDegreeSequence() {
        System.out.println("getDegreeSequence");
        Graph instance = new Graph();
        int[] expResult = null;
        int[] result = instance.getDegreeSequence();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
