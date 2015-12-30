/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.GuiConstants;
import GraphTheory.Input.MouseGestures;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.UIComponents.RenderingsManager;
import GraphTheory.Utility.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Thayer
 */
public class Graph implements GraphObject, Translatable {

    /**
     * The default brush size for vertices and edges that are elements of this
     * graph.
     */
    protected int strokeSize = 4;

    /**
     * The default size of each vertices that is an element of this graph.
     */
    protected int vertexSize = 8;

    /**
     * The sum of all degrees of vertices in the graph.
     */
    protected int totalDegree = 0;

    /**
     * The x coordinate that is used as an anchor point for the graph.
     */
    protected double centerX;

    /**
     * The y coordinate that is used as an anchor point for the graph.
     */
    protected double centerY;
    protected double defaultRadius = 100;
    public GraphCircle circle;
    public Pane graphContents = new Pane();
    private ArrayList<GraphVertex> vertexSet = new ArrayList();
    private ArrayList<GraphEdge> edgeSet = new ArrayList();
    private ArrayList<GraphEdge> edges = new ArrayList();
    //private ArrayList<Graph> subgraphs = new ArrayList();

    public SimpleStringProperty orderProperty = new SimpleStringProperty("0");
    public SimpleStringProperty sizeProperty = new SimpleStringProperty("0");
    public SimpleStringProperty densityProperty = new SimpleStringProperty("0");
    private static RenderingsManager renderingsManager;

    public ArrayList<GraphVertex> getVertexSet() {
        return vertexSet;
    }

    public ArrayList<GraphEdge> getEdgeSet() {
        return edgeSet;
    }

    public ArrayList<GraphEdge> getEdgeList() {
        return edges;
    }

    public double getDefaultRadius() {
        return defaultRadius;
    }

    public void reorderVertexs() {
        reorderVertexs(defaultRadius);
    }

    public void reorderVertexs(double radius) {
        defaultRadius = radius;
        double radians = 2 * Math.PI / vertexSet.size(); //2pi radians in a circle, divided into order number of arcs
        for (int i = 0; i < vertexSet.size(); i++) {
            vertexSet.get(i).setPosition(findPoint2D(radians * i));
        }
    }

    public Graph() {
        this(GuiConstants.RENDERINGS_WIDTH / 2, GuiConstants.RENDERINGS_HEIGHT / 2);
    }

    public Graph(int order){
        this(GuiConstants.RENDERINGS_WIDTH / 2, GuiConstants.RENDERINGS_HEIGHT / 2, order);
    }

    /**
     * Create an empty graph centered about (x,y).
     *
     * @param x
     * @param y
     */
    public Graph(double x, double y) {
        centerX = x;
        centerY = y;
        circle = new GraphCircle(vertexSize / 2, this);
        circle.setStroke(Color.GRAY);
        circle.setFill(Color.GRAY);
        circle.setCenterX(x);
        circle.setCenterY(y);
        graphContents.getChildren().add(circle);
        renderingsManager.addNode(circle);
    }

    /**
     * Create an empty graph of a given order centered about (x,y).
     *
     * @param x
     * @param y
     * @param order
     */
    public Graph(double x, double y, int order) {
        this(x, y);
        if (order < 0) {
            throw new IllegalArgumentException("For some (p,q) graph p cannot be negative.");
        }
        double radians = 2 * Math.PI / order; //2pi radians in a circle, divided into order number of arcs
        for (int i = 0; i < order; i++) {
            addVertex(findPoint2D(i * radians));
        }
    }

    public static void setRenderer(RenderingsManager m){
        renderingsManager = m;
    }

    /**
     *
     * @param x
     * @param y
     * @param order
     * @return
     */
    public static Graph buildKGraph(double x, double y, int order) {
        Graph g = new Graph(x, y, order);
        for (GraphEdge e : g.getEdgeList()) {
            g.drawEdge(e);
        }
        for (GraphVertex v : g.getVertexSet()) {
            v.circle.toFront();
        }
        return g;
    }

    public static Graph buildKGraph(int order) {
        return buildKGraph(GuiConstants.RENDERINGS_WIDTH / 2,
                GuiConstants.RENDERINGS_HEIGHT / 2, order);
    }

    /**
     * Create a new vertex that is an element of this graph, centered at (x,y).
     *
     * @param x
     * @param y
     * @return a vertex at the specified location
     */
    public GraphVertex addVertex(double x, double y) {
        GraphVertex tempVertex = new GraphVertex(this, x, y);
        MouseGestures.addGestures(tempVertex);
        graphContents.getChildren().add(tempVertex.circle);
        renderingsManager.addNode(tempVertex.circle);

        newVertexSetup(tempVertex);

        return tempVertex;
    }

    private void newVertexSetup(GraphVertex u){
        for (GraphVertex v : vertexSet) {
            addEdge(v, u);
        }
        vertexSet.add(u);
        vertexSetChanged();
    }

    public void addVertex(GraphVertex u){
        newVertexSetup(u);
    }

    /**
     * Create a new vertex that is an element of this graph, at a given point.
     *
     * @param p
     */
    public void addVertex(Point2D p) {
        addVertex(p.getX(), p.getY());
    }

    /**
     * Add an edge between two given points, of a specified color.
     *
     * @param start
     * @param end
     * @param color
     * @return a new GraphEdge
     */
    public GraphEdge addEdge(GraphVertex start, GraphVertex end, Color color) {
        if (start == null || end == null || color == null) {
            throw new IllegalArgumentException("One of the arguments was null");
        }
        if (start == end) {
            throw new IllegalArgumentException("A vertex cannot be adjacent to itself.");
        }
        GraphEdge tempEdge = new GraphEdge(this, start, end, color);
        edges.add(tempEdge);
        start.addEdge(tempEdge);
        end.addEdge(tempEdge);
        MouseGestures.addGestures(tempEdge);

        return tempEdge;
    }

    /**
     * Add an edge between two given points
     *
     * @param start
     * @param end
     */
    public GraphEdge addEdge(GraphVertex start, GraphVertex end) {
        return addEdge(start, end, Color.BLACK);
    }

    public GraphEdge drawEdge(GraphVertex start, GraphVertex end) {
        GraphEdge e = findEdge(start, end);
        if(e == null){
            e = getEdge(start,end);
            drawEdge(e);
            return e;
        }else{
            Logger.log("Attempted to add an edge that was already present.");
            return null;
        }
    }

    public void drawEdge(GraphEdge e) {
        e.active = true;
        edgeSet.add(e);
        e.startNode.addEdge(e);
        e.endNode.addEdge(e);
        edgeSetChanged();
        e.startNode.addAdjacent(e.endNode);
        e.endNode.addAdjacent(e.startNode);

        graphContents.getChildren().add(e.line);
        renderingsManager.addNode(e.line);
    }

    public boolean deleteEdge(GraphEdge e) {
        Logger.log("Deleting edge.");
        removeEdge(e);
        edges.remove(e);
        return true;
    }

    public boolean removeEdge(GraphEdge e) {
        if (elementOf(e)) {
            e.active = false;
            e.startNode.getAdjacentTo().remove(e.endNode);
            e.endNode.getAdjacentTo().remove(e.startNode);
            edgeSet.remove(e);
            this.graphContents.getChildren().remove(e.line);
            renderingsManager.removeNode(e.line);
            edgeSetChanged();
            return true;
        }
        return false;
    }

    /**
     * The number of vertices in the vertex set.
     *
     * @return
     */
    public int order() {
        return vertexSet.size();
    }

    /**
     * The number of edges in the edge set.
     *
     * @return
     */
    public int size() {
        return edgeSet.size();
    }

    public static int maxEdges(int order) {
        return (order * (order - 1) / 2); // (p * (p-1)) / 2 is the sum of all ints [1..order]
    }                                     // standard formula for max q of a (p,q) graph

    public static int maxEdges(Graph g) {
        return maxEdges(g.order());
    }

    /**
     * Uses the unit circle to find
     *
     * @param rad
     * @return
     */
    protected Point2D findPoint2D(double rad) {
        return new Point2D(centerX + (Math.cos(rad) * defaultRadius), centerY + (Math.sin(rad) * defaultRadius));
    }

    public void complement() {
        for (GraphEdge n : edges) {
            GraphEdge temp = findEdge(n);
            if (temp != null) {
                this.removeEdge(temp);
            } else {
                drawEdge(n);
            }
        }
    }

    public boolean elementOf(GraphVertex v) {
        for (GraphVertex u : vertexSet) {
            if (u == v || u.equals(v)){
                return true;
            }
        }
        return false;
    }

    public boolean elementOf(GraphEdge e) {
        for (GraphEdge u : edgeSet) {
            if (u == e) {
                return true;
            }
        }
        return false;
    }

    public GraphEdge getEdge(GraphVertex u, GraphVertex v){
        return getEdge(new GraphEdge(this,u,v));
    }
    
    public GraphEdge findEdge(GraphVertex u, GraphVertex v) {
        GraphEdge tempEdge = new GraphEdge(this, u, v);
        return findEdge(tempEdge);
    }

    public GraphEdge getEdge(GraphEdge e) {
        for (GraphEdge n : edges) {
            if (n.equals(e)) {
                return n;
            }
        }

        return null;
    }

    public GraphEdge findEdge(GraphEdge e) {
        for (GraphEdge n : edgeSet) {
            if (n.equals(e)) {
                return n;
            }
        }

        return null;
    }

    //Sets the center handle to the average coordinates among all vertices
    public void recenterCircle() {
        double newSumX = 0, newSumY = 0;
        int divisor = vertexSet.size();
        for (GraphVertex v : vertexSet) {
            newSumX += v.getX();
            newSumY += v.getY();
        }
        circle.setCenterX(newSumX / divisor);
        circle.setCenterY(newSumY / divisor);
    }

    @Override
    public void translate(double x, double y) {
        circle.translate(x, y);
        for (GraphVertex v : vertexSet) {
            v.translate(x, y);
        }
    }

    public void toFront() {
        for (GraphEdge e : edgeSet) {
            //e.line.toFront();Known issue that toFront() doesn't work
            renderingsManager.toFront(e.line);
        }
        for (GraphVertex c : vertexSet) {
            //c.circle.toFront();Known issue that toFront() doesn't work
            renderingsManager.toFront(c.circle);
        }
    }

    public void verticesToFront() {
        for (GraphVertex c : vertexSet) {
            renderingsManager.toFront(c.circle);
        }
    }

    public boolean removeVertex(GraphVertex v) {
        if (elementOf(v)) {
            for (GraphEdge e : v.getEdges()) {
                deleteEdge(e);
            }
            for (GraphVertex n : v.getAdjacentTo()) {
                n.getAdjacentTo().remove(v);
            }
            vertexSet.remove(v);
            renderingsManager.removeNode(v.circle);
            vertexSetChanged();
            return true;
        }
        return false;
    }

    public void vertexSetChanged() {
        recenterCircle();

        orderProperty.set(Integer.toString(vertexSet.size()));
        densityProperty.set(String.format("%.3f", (double) edgeSet.size() / (double) edges.size()));
    }

    public void edgeSetChanged() {
        sizeProperty.set(Integer.toString(edgeSet.size()));
        densityProperty.set(String.format("%.3f", (double) edgeSet.size() / (double) edges.size()));
    }

    public int[] getDegreeSequence() {
        int[] degSeq = new int[vertexSet.size()];
        for (int i = 0; i < vertexSet.size(); i++) {
            degSeq[i] = vertexSet.get(i).getDegree();
        }
        Arrays.sort(degSeq);
        return degSeq;
    }

    public int[][] getAdjacencyMatrix(){
        int size = vertexSet.size();
        int[][] adj = new int[size][size];//default initialized to zero
        
        for(GraphEdge e : edgeSet){
            int startIndex, endIndex;
            
            startIndex = vertexSet.indexOf(e.startNode);
            endIndex   = vertexSet.indexOf(e.endNode);

            adj[startIndex][endIndex] = e.getLength();
            adj[endIndex][startIndex] = e.getLength();
        }
        return adj;
    }

    public static boolean isomorphic(GraphEntity g1, GraphEntity g2){
        return isomorphic(g1.getGraph(), g2.getGraph());
    }

    public static boolean isomorphic(Graph g1, Graph g2) {
        Logger.log("Checking isomorphism between graphs.");
        if(g1.getEdgeSet().size()!=g2.edgeSet.size())
            return false;
        if (Arrays.equals(g1.getDegreeSequence(), g2.getDegreeSequence())) {
            return isoHelper(g1.getAdjacencyMatrix(),g2.getAdjacencyMatrix());
        }
        return false;
    }

    private static ArrayList<Map<Integer,Integer>> generateMutations(int size){
        ArrayList<Integer> unmatched = new ArrayList<>(); 
        for(int i=0; i<size;i++)
           unmatched.add(i);
        
        ArrayList<Map<Integer,Integer>> muts = new ArrayList<>();
        mutBuilder(0,unmatched,new HashMap<>(), muts);//helper function for recursive building vertex mappings
        
        return muts;
    }

    private static void mutBuilder(int index, ArrayList<Integer> unmatched,
            Map<Integer,Integer> mapping, ArrayList<Map<Integer,Integer>> finished){
        for(Integer i : unmatched){
            Map<Integer,Integer> newMapping = new HashMap(mapping);
            newMapping.put(index, i);

            ArrayList<Integer> stillUnmatched = new ArrayList(unmatched);
            stillUnmatched.remove(i);

            if(unmatched.size() == 1){
                finished.add(newMapping);
            }

            mutBuilder(index+1,stillUnmatched,newMapping,finished);
        }
    }

    private static boolean isoHelper(int[][] adj1,int[][]adj2){//ArrayList<BitSet> adj1, ArrayList<BitSet> adj2){
        ArrayList<Map<Integer,Integer>> muts = generateMutations(adj1.length);
        boolean mappingFound = false;
        for(Map mapping : muts){
            if(compareMatrixMutation(adj1,adj2,mapping))
                return true;
        }
        return false;
    }

    private static boolean compareMatrixMutation(int[][] adj1, int[][] adj2, 
            Map<Integer,Integer> mapping){
        for(int i = 0; i < adj1.length; i++){
            for(int j= 0; j < adj2.length; j++){
                if(adj1[i][j]!=adj2[mapping.get(i)][mapping.get(j)])
                    return false;
            }
        }
        return true;
    }

    public Node getCircle(){
        return circle;
    }

    public void updateContentParents(){
        for(GraphVertex v : vertexSet){
            v.setParent(this);
        }
        for(GraphEdge e : edgeSet){
            e.setParent(this);
        }
    }
}
