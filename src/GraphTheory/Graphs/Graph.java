/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.Mouse.MouseGestures;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Thayer
 */
public class Graph implements GraphObject,Translatable{

    /**
     * The default brush size for nodes and edges that are elements of this graph.
     */
    protected int strokeSize = 4;

    /**
     * The default size of each node that is an element of this graph.
     */
    protected int nodeSize = 8;

    /**
     * The sum of all degrees of nodes in the graph.
     */
    protected int totalDegree = 0;

    /**
     * The x coordinate that is used as an anchor point for the graph.
     */
    protected double centerX;

    /**
     *The y coordinate that is used as an anchor point for the graph.
     */
    protected double centerY;
    protected double defaultRadius = 100;
    public GraphCircle circle;
    public Pane graphContents = new Pane();
    private ArrayList<GraphNode> nodeSet = new ArrayList();
    private ArrayList<GraphEdge> edgeSet = new ArrayList();
    //private ArrayList<Graph> subgraphs = new ArrayList();

    public ArrayList<GraphNode> getNodeSet() {
        return nodeSet;
    }

    public ArrayList<GraphEdge> getEdgeSet() {
        return edgeSet;
    }

    public double getDefaultRadius() {
        return defaultRadius;
    }
    
    public void reorderNodes(){
        reorderNodes(defaultRadius);
    }
    
    public void reorderNodes(double radius){
        defaultRadius = radius;
        double radians = 2 * Math.PI / nodeSet.size(); //2pi radians in a circle, divided into order number of arcs
        for(int i = 0; i < nodeSet.size(); i++){
            nodeSet.get(i).setPosition(findPoint2D(radians * i));
        }
    }
    
    /**
     * Create an empty graph centered about (x,y).
     * @param x
     * @param y
     */
    public Graph(double x, double y){
        centerX = x;
        centerY = y;
        circle = new GraphCircle(nodeSize,this);
        circle.setStroke(Color.GRAY);
        circle.setFill(Color.GRAY);
        circle.setCenterX(x);
        circle.setCenterY(y);
        graphContents.getChildren().add(circle);
    }
    
    /**
     * Create an empty graph of a given order centered about (x,y).
     * @param x
     * @param y
     * @param order
     */
    public Graph(double x, double y, int order){
        this(x,y);
        if(order < 0)
            throw new IllegalArgumentException("For some (p,q) graph p cannot be negative.");
        double radians = 2 * Math.PI / order; //2pi radians in a circle, divided into order number of arcs
        for(int i = 0; i < order; i++){
            addNode(findPoint2D(i*radians));
        }
    }
    
    /**
     *
     * @param x
     * @param y
     * @param order
     * @return
     */
    public static Graph buildKGraph(double x, double y, int order){
        Graph g = new Graph(x,y,order);
        ArrayList<GraphNode> vertices = g.getNodeSet();
        for(int i = 0; i < order; i++){
            for(int j = (i+1); j < order; j++){
                g.addEdge(vertices.get(i), vertices.get(j));
            }
        }
        for(GraphNode v : vertices){
            v.circle.toFront();
        }
        return g;
    }
    
    /**
     * Create a new node that is an element of this graph, centered at (x,y).
     * @param x
     * @param y
     */
    public void addNode(double x, double y){
        GraphNode tempNode = new GraphNode(this,x,y);
        MouseGestures.addGestures(tempNode);
        nodeSet.add(tempNode);
        graphContents.getChildren().add(tempNode.circle);
    }
    
    /**
     * Create a new node that is an element of this graph, at a given point.
     * @param p
     */
    public void addNode(Point2D p){
        addNode(p.getX(),p.getY());
    }
    
    /**
     * Add an edge between two given points, of a specified color.
     * @param start
     * @param end
     * @param color
     */
    public void addEdge(GraphNode start, GraphNode end, Color color){        
        if(start==null||end==null||color==null)
            throw new IllegalArgumentException("One of the arguments was null");
        if(start==end)
            throw new IllegalArgumentException("A vertex cannot be adjacent to itself.");
        GraphEdge tempEdge = new GraphEdge(this, start, end, color);
        edgeSet.add(tempEdge);
        start.addEdge(tempEdge);
        end.addEdge(tempEdge);
        MouseGestures.addGestures(tempEdge);
        graphContents.getChildren().add(tempEdge.line);
    }
    
    /**
     * Add an edge between two given points
     * @param start
     * @param end
     */
    public void addEdge(GraphNode start, GraphNode end){
        addEdge(start,end,Color.BLACK);
    }
    
    /**
     * The number of nodes in the vertex set.
     * @return
     */
    public int order(){
        return nodeSet.size();
    }
    
    /**
     * The number of edges in the edge set.
     * @return
     */
    public int size(){
        return edgeSet.size();
    }
    
    public static int maxEdges(int order){
        return (order * (order - 1) / 2); // (p * (p-1)) / 2 is the sum of all ints [1..order]
    }                                     // standard formula for max q of a (p,q) graph
    
    public static int maxEdges(Graph g){
        return maxEdges(g.order());
    }
    
    /**
     * Uses the unit circle to find 
     * @param rad
     * @return
     */
    protected Point2D findPoint2D(double rad){
        return new Point2D(centerX + (Math.cos(rad) * defaultRadius),centerY + (Math.sin(rad) * defaultRadius));
    }
    
    //Sets the center handle to the average coordinates among all vertices
    public void recenterCircle(){
        double newSumX = 0, newSumY = 0;
        int divisor = nodeSet.size();
        for(GraphNode v : nodeSet){
            newSumX += v.getX();
            newSumY += v.getY();
        }
        circle.setCenterX(newSumX / divisor);
        circle.setCenterY(newSumY / divisor);
    }

    @Override
    public void translate(double x, double y) {
        circle.translate(x,y);
        for(GraphNode v : nodeSet){
            v.translate(x,y);
        }
    }
}
