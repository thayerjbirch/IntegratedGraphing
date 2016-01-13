/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.UIComponents.RenderingsManager;
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
    Graph testSubject;

    public GraphTest() {
        testSubject = new Graph();
        for(int i = 0; i < 10; i++)
            testSubject.addVertex(new GraphVertex(testSubject, 0, 0));

        testSubject.drawEdge(testSubject.getVertexSet().get(4),
                            testSubject.getVertexSet().get(5));
        testSubject.drawEdge(testSubject.getVertexSet().get(3),
                            testSubject.getVertexSet().get(5));
        testSubject.drawEdge(testSubject.getVertexSet().get(2),
                            testSubject.getVertexSet().get(5));
        testSubject.drawEdge(testSubject.getVertexSet().get(1),
                            testSubject.getVertexSet().get(5));
    }
    
    @BeforeClass
    public static void setUpClass() {
        Graph.setRenderer(new RenderingsManager());
    }
    
    @AfterClass
    public static void tearDownClass() {
        Graph.setRenderer(null);
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
        Point2D expResult = new Point2D(100,0);
        Point2D result = instance.findPoint2D(rad);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVertexSet method, of class Graph.
     */
    @Test
    public void testGetVertexSet() {
        System.out.println("getVertexSet");
        assertEquals(true, testSubject.getVertexSet() instanceof ArrayList);
    }

    /**
     * Test of getEdgeSet method, of class Graph.
     */
    @Test
    public void testGetEdgeSet() {
        System.out.println("getEdgeSet");
        assertEquals(true, testSubject.getEdgeSet() instanceof ArrayList);
    }

    /**
     * Test of getDefaultRadius method, of class Graph.
     */
    @Test
    public void testGetDefaultRadius() {
        System.out.println("getDefaultRadius");
        assertEquals(true, testSubject.getDefaultRadius() > 0);
    }

    /**
     * Test of reorderVertexs method, of class Graph.
     */
    @Test
    public void testReorderVertexs_0args() {
        System.out.println("reorderVertexs");
        testSubject.reorderVertexs();
    }

    /**
     * Test of reorderVertexs method, of class Graph.
     */
    @Test
    public void testReorderVertexs_double() {
        System.out.println("reorderVertexs");
        testSubject.reorderVertexs(0);
        testSubject.reorderVertexs(1000);
        testSubject.reorderVertexs(-10);
    }

    /**
     * Test of buildKGraph method, of class Graph.
     */
    @Test
    public void testBuildKGraph_3args() {
        System.out.println("buildKGraph");
        double x = 0.0;
        double y = 0.0;
        int order = 6;
        Graph graph = Graph.buildKGraph(x, y, order);
        assertEquals(6, graph.order());
        assertEquals(15, graph.size());
    }

    /**
     * Test of buildKGraph method, of class Graph.
     */
    @Test
    public void testBuildKGraph_int() {
        System.out.println("buildKGraph");
        int order = 7;
        Graph graph = Graph.buildKGraph(order);
        assertEquals(7, graph.order());
        assertEquals(21, graph.size());
    }

    /**
     * Test of addVertex method, of class Graph.
     */
    @Test
    public void testAddVertex_double_double() {
        System.out.println("addVertex");
        double x = 0.0;
        double y = 0.0;
        int order = testSubject.order();
        testSubject.addVertex(x, y);
        assertEquals(order+1, testSubject.order());
    }

    /**
     * Test of drawEdge method, of class Graph.
     * This method is a convenience method, and as such the tests
     * are covering invalid input
     */
    @Test
    public void testDrawEdge_GraphVertex_GraphVertex() {
        System.out.println("drawEdge");
        GraphVertex start = testSubject.getVertexSet().get(4);
        GraphVertex end = testSubject.getVertexSet().get(5);

        assertEquals(null, testSubject.drawEdge(start, end));
    }

    /**
     * Test of drawEdge method, of class Graph.
     */
    @Test
    public void testDrawEdge_GraphEdge() {
        System.out.println("drawEdge");
        GraphEdge e = testSubject.getEdge(testSubject.getVertexSet().get(0),
                                          testSubject.getVertexSet().get(8));
        int size = testSubject.size();
        testSubject.drawEdge(e);

        assertEquals(size + 1, testSubject.size());
    }

    /**
     * Test of deleteEdge method, of class Graph.
     */
    @Test
    public void testDeleteEdge() {
        System.out.println("deleteEdge");
        GraphEdge e = testSubject.getEdgeSet().get(0);
        int edgeSetSize = testSubject.getEdgeSet().size();
        int edgeListSize = testSubject.getEdgeList().size();//number of edges in K graph of that order
        assertEquals(true, testSubject.deleteEdge(e));
        assertEquals(edgeSetSize - 1, testSubject.getEdgeSet().size());
        assertEquals(edgeListSize -1, testSubject.getEdgeList().size());
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("removeEdge");
        GraphEdge e = null;
        Graph instance = new Graph();
        instance.addVertex(new GraphVertex(instance,0,0));
        instance.addVertex(new GraphVertex(instance,0,0));
        instance.drawEdge(instance.getVertexSet().get(0),
                         instance.getVertexSet().get(1));
        
        boolean expResult = false;
        boolean result = instance.removeEdge(e);
        assertEquals(expResult, result);

        //removing an edge from a different graph
        expResult = false;
        result = testSubject.removeEdge(instance.getEdgeSet().get(0));
        assertEquals(expResult,result);

        expResult = true;
        result = testSubject.removeEdge(testSubject.getEdgeSet().get(0));
        assertEquals(expResult,result);
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
    }

    /**
     * Test of maxEdges method, of class Graph.
     */
    @Test
    public void testMaxEdges_int() {
        System.out.println("maxEdges");
        int order = 4;
        int expResult = 6;
        int result = Graph.maxEdges(order);
        assertEquals(expResult, result);

        order = 6;
        expResult = 15;
        assertEquals(expResult, Graph.maxEdges(6));
    }

    /**
     * Test of maxEdges method, of class Graph.
     */
    @Test
    public void testMaxEdges_Graph() {
        System.out.println("maxEdges");
        Graph g = Graph.buildKGraph(4);
        int expResult = 6;
        int result = Graph.maxEdges(g);
        assertEquals(expResult, result);

        g = Graph.buildKGraph(6);
        expResult = 15;
        assertEquals(expResult, Graph.maxEdges(g));
    }

    /**
     * Test of complement method, of class Graph.
     */
    @Test
    public void testComplement() {
        System.out.println("complement");
        Graph graph = Graph.buildKGraph(6);
        graph.complement();
        assertEquals(0,graph.size());
        graph.complement();
        assertEquals(15,graph.size());
    }

    /**
     * Test of elementOf method, of class Graph.
     */
    @Test
    public void testElementOf_GraphVertex() {
        System.out.println("elementOf");
        Graph instance = new Graph();
        GraphVertex v = new GraphVertex(instance, 0 ,0);
        assertEquals(false, testSubject.elementOf(v));
        
        assertEquals(true, testSubject.getVertexSet().get(0) instanceof GraphVertex);
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

        assertEquals(true, testSubject.elementOf(testSubject.getEdgeSet().get(0)));
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

        assertEquals(false, null != testSubject.findEdge(
                testSubject.getVertexSet().get(0), testSubject.getVertexSet().get(1)));
    }

    /**
     * Test of findEdge method, of class Graph.
     */
    @Test
    public void testFindEdge_GraphEdge() {
        System.out.println("findEdge");
        assertEquals(null, testSubject.findEdge(null));
        assertEquals(true, testSubject.findEdge(testSubject.getEdgeSet().get(0))
                            instanceof GraphEdge);
    }

    /**
     * Test of recenterCircle method, of class Graph.
     */
    @Test
    public void testRecenterCircle() {
        System.out.println("recenterCircle");
        Graph instance = new Graph();
        instance.recenterCircle();
    }

    /**
     * Test of translate method, of class Graph.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double x = 20.0;
        double y = 10.0;
        Graph instance = new Graph();
        instance.translate(x, y);
    }

    /**
     * Test of toFront method, of class Graph.
     */
    @Test
    public void testToFront() {
        System.out.println("toFront");
        testSubject.toFront();
    }

    /**
     * Test of verticesToFront method, of class Graph.
     */
    @Test
    public void testVerticesToFront() {
        System.out.println("verticesToFront");
        testSubject.verticesToFront();
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("removeVertex");
        Graph instance = new Graph();
        GraphVertex v = new GraphVertex(instance,0,0);
        instance.addVertex(v);
        int order = instance.order();
        instance.removeVertex(v);
        assertEquals(order - 1, instance.order());
    }

    /**
     * Test of vertexSetChanged method, of class Graph.
     * Collection of units of work to be done together.
     * This is just a high level test for errors.
     */
    @Test
    public void testVertexSetChanged() {
        System.out.println("vertexSetChanged");
        testSubject.vertexSetChanged();
    }

    /**
     * Test of edgeSetChanged method, of class Graph.
     * Collection of units of work to be done together.
     * This is just a high level test for errors.
     */
    @Test
    public void testEdgeSetChanged() {
        System.out.println("edgeSetChanged");
        testSubject.edgeSetChanged();
    }

    /**
     * Test of getDegreeSequence method, of class Graph.
     */
    @Test
    public void testGetDegreeSequence() {
        System.out.println("getDegreeSequence");
        int length = testSubject.order();
        int[] degSeq = testSubject.getDegreeSequence();
        assertEquals(length, degSeq.length);
    }

    /**
     * Test of getEdgeList method, of class Graph.
     * This method returns all potential edges for the graph.
     */
    @Test
    public void testGetEdgeList() {
        System.out.println("getEdgeList");
        ArrayList<GraphEdge> list = testSubject.getEdgeList();
        int order = testSubject.order();
        int maxSize = (order * (order - 1)) / 2;
        assertEquals(true, list != null);
        assertEquals(maxSize, list.size());
    }

    /**
     * Test of setRenderer method, of class Graph.
     */
    @Test
    public void testSetRenderer() {
        System.out.println("setRenderer");
        RenderingsManager m = new RenderingsManager();
        Graph.setRenderer(m);
    }

    /**
     * Test of addVertex method, of class Graph.
     */
    @Test
    public void testAddVertex_GraphVertex() {
        System.out.println("addVertex");
        int order = testSubject.order();
        GraphVertex v = new GraphVertex(testSubject,0,0);
        testSubject.addVertex(v);
        assertEquals(order + 1, testSubject.order());
        assertEquals(true, testSubject.elementOf(v));
    }

    /**
     * Test of getContents method, of class Graph.
     */
    @Test
    public void testGetContents() {
        System.out.println("getContents");
        assertEquals(false,testSubject.getContents()==null);
    }

    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge_GraphVertex_GraphVertex() {
        System.out.println("getEdge");
        
    }

    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge_GraphEdge() {
        System.out.println("getEdge");
        ArrayList<GraphVertex> vSet = testSubject.getVertexSet();
        GraphEdge e = new GraphEdge(testSubject, vSet.get(6), vSet.get(7));

        assertEquals(false, testSubject.getEdge(e) == null);
    }

    /**
     * Test of getAdjacencyMatrix method, of class Graph.
     */
    @Test
    public void testGetAdjacencyMatrix() {
        System.out.println("getAdjacencyMatrix");
        int[][] adj = testSubject.getAdjacencyMatrix();
        int order = testSubject.order();
        assertEquals(order, adj.length);
        assertEquals(order, adj[0].length);

        GraphEdge e = testSubject.getEdgeSet().get(0);
        int s = testSubject.getVertexSet().indexOf(e.getStartNode());
        int f = testSubject.getVertexSet().indexOf(e.getEndNode());
        int length = e.getLength();
        assertEquals(length, adj[s][f]);
        assertEquals(length, adj[f][s]);
    }

    /**
     * Test of isomorphic method, of class Graph.
     */
    @Test
    public void testIsomorphic_GraphEntity_GraphEntity() {
        System.out.println("isomorphic");
        assertEquals(false, Graph.isomorphic(null, (GraphEntity)null));
    }

    /**
     * Test of isomorphic method, of class Graph.
     */
    @Test
    public void testIsomorphic_Graph_Graph() {
        System.out.println("isomorphic");
        Graph k4 = Graph.buildKGraph(4);
        assertEquals(false, Graph.isomorphic(k4, testSubject));

        Graph triangles = new Graph(6);
        ArrayList<GraphVertex> triVSet = triangles.getVertexSet();
        Graph hexagon = new Graph(6);
        ArrayList<GraphVertex> hexVSet = hexagon.getVertexSet();

        triangles.drawEdge(triVSet.get(0), triVSet.get(1));
        triangles.drawEdge(triVSet.get(1), triVSet.get(2));
        triangles.drawEdge(triVSet.get(2), triVSet.get(0));

        triangles.drawEdge(triVSet.get(3), triVSet.get(4));
        triangles.drawEdge(triVSet.get(4), triVSet.get(5));
        triangles.drawEdge(triVSet.get(5), triVSet.get(3));

        hexagon.drawEdge(hexVSet.get(0), hexVSet.get(1));
        hexagon.drawEdge(hexVSet.get(1), hexVSet.get(2));
        hexagon.drawEdge(hexVSet.get(2), hexVSet.get(3));
        hexagon.drawEdge(hexVSet.get(3), hexVSet.get(4));
        hexagon.drawEdge(hexVSet.get(4), hexVSet.get(5));
        hexagon.drawEdge(hexVSet.get(5), hexVSet.get(0));

        assertEquals(false, Graph.isomorphic(triangles, hexagon));

        assertEquals(true, Graph.isomorphic(k4, Graph.buildKGraph(4)));
    }

    /**
     * Test of getCircle method, of class Graph.
     */
    @Test
    public void testGetCircle() {
        System.out.println("getCircle");
        assertEquals(false, testSubject.getCircle() == null);
    }

    /**
     * Test of updateContentParents method, of class Graph.
     */
    @Test
    public void testUpdateContentParents() {
        System.out.println("updateContentParents");
        testSubject.updateContentParents();
        assertEquals(testSubject, testSubject.getEdgeSet().get(0).getParent());
    }
    
}
