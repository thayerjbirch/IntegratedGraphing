/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.GuiConstants;
import GraphTheory.Mouse.MouseGestures;
import GraphTheory.UIComponents.RenderingsManager;
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
     * The default brush size for vertices and edges that are elements of this graph.
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
     *The y coordinate that is used as an anchor point for the graph.
     */
    protected double centerY;
    protected double defaultRadius = 100;
    public GraphCircle circle;
    public Pane graphContents = new Pane();
    private ArrayList<GraphVertex> vertexSet = new ArrayList();
    private ArrayList<GraphEdge> edgeSet = new ArrayList();
    private ArrayList<GraphEdge> edges   = new ArrayList();
    //private ArrayList<Graph> subgraphs = new ArrayList();

    public ArrayList<GraphVertex> getVertexSet() {
        return vertexSet;
    }

    public ArrayList<GraphEdge> getEdgeSet() {
        return edgeSet;
    }
    
    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }
    public double getDefaultRadius() {
        return defaultRadius;
    }
    
    public void reorderVertexs(){
        reorderVertexs(defaultRadius);
    }
    
    public void reorderVertexs(double radius){
        defaultRadius = radius;
        double radians = 2 * Math.PI / vertexSet.size(); //2pi radians in a circle, divided into order number of arcs
        for(int i = 0; i < vertexSet.size(); i++){
            vertexSet.get(i).setPosition(findPoint2D(radians * i));
        }
    }
    
    public Graph(){
        this(GuiConstants.RENDERINGS_WIDTH / 2,GuiConstants.RENDERINGS_HEIGHT / 2);
    }
    
    /**
     * Create an empty graph centered about (x,y).
     * @param x
     * @param y
     */
    public Graph(double x, double y){
        centerX = x;
        centerY = y;
        circle = new GraphCircle(vertexSize/2,this);
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
            addVertex(findPoint2D(i*radians));
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
        for(GraphEdge e : g.getEdges()){
            g.drawEdge(e);
        }
        for(GraphVertex v : g.getVertexSet()){
            v.circle.toFront();
        }
        return g;
    }
    
    public static Graph buildKGraph(int order){
        return buildKGraph(GuiConstants.RENDERINGS_WIDTH / 2,
                           GuiConstants.RENDERINGS_HEIGHT / 2, order);
    }
    
    /**
     * Create a new vertex that is an element of this graph, centered at (x,y).
     * @param x
     * @param y
     */
    public GraphVertex addVertex(double x, double y){
        GraphVertex tempVertex = new GraphVertex(this,x,y);
        MouseGestures.addGestures(tempVertex);
        for(GraphVertex v : vertexSet)
            addEdge(v, tempVertex);
        vertexSet.add(tempVertex);
        graphContents.getChildren().add(tempVertex.circle);
        
        return tempVertex;
    }
    
    /**
     * Create a new vertex that is an element of this graph, at a given point.
     * @param p
     */
    public void addVertex(Point2D p){
        addVertex(p.getX(),p.getY());
    }
    
    /**
     * Add an edge between two given points, of a specified color.
     * @param start
     * @param end
     * @param color
     * @return a new GraphEdge
     */
    public GraphEdge addEdge(GraphVertex start, GraphVertex end, Color color){        
        if(start==null||end==null||color==null)
            throw new IllegalArgumentException("One of the arguments was null");
        if(start==end)
            throw new IllegalArgumentException("A vertex cannot be adjacent to itself.");
        GraphEdge tempEdge = new GraphEdge(this, start, end, color);
        edges.add(tempEdge);
        start.addEdge(tempEdge);
        end.addEdge(tempEdge);
        MouseGestures.addGestures(tempEdge);
        
        return tempEdge;
    }
    
    /**
     * Add an edge between two given points
     * @param start
     * @param end
     */
    public void addEdge(GraphVertex start, GraphVertex end){
        addEdge(start,end,Color.BLACK);
    }
    
    public void drawEdge(GraphEdge e){
        e.active = true;
        edgeSet.add(e);
        graphContents.getChildren().add(e.line);
    }
    
    public void removeEdge(GraphEdge e){
        e.active = false;
        edgeSet.remove(e);
        this.graphContents.getChildren().remove(e.line);
    }
    
    /**
     * The number of vertices in the vertex set.
     * @return
     */
    public int order(){
        return vertexSet.size();
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
    
    public void compliment(){
        for(GraphEdge n : edges){
            GraphEdge temp = findEdge(n);
            if(temp!=null){
                this.removeEdge(temp);
            }
            else{
                drawEdge(n);
            }
        }
    }
    
   
    private GraphEdge elementOf(GraphEdge e){
        for(GraphEdge n : edgeSet){
            if(n.equals(e))
                return n;
        }
        return null;
    }
    
    public GraphEdge findEdge(GraphVertex u, GraphVertex v){
        GraphEdge tempEdge = new GraphEdge(this, u, v);
        return this.elementOf(tempEdge);
    }
    
    public GraphEdge findEdge(GraphEdge e){
        return this.elementOf(e);
    }
    
    //Sets the center handle to the average coordinates among all vertices
    public void recenterCircle(){
        double newSumX = 0, newSumY = 0;
        int divisor = vertexSet.size();
        for(GraphVertex v : vertexSet){
            newSumX += v.getX();
            newSumY += v.getY();
        }
        circle.setCenterX(newSumX / divisor);
        circle.setCenterY(newSumY / divisor);
    }

    @Override
    public void translate(double x, double y) {
        circle.translate(x,y);
        for(GraphVertex v : vertexSet){
            v.translate(x,y);
        }
    }
    
    public void toFront(){
        System.out.println(edgeSet.size() + "," + vertexSet.size());
        for(GraphEdge e : edgeSet){
            //e.line.toFront();Known issue that toFront() doesn't work
            RenderingsManager.toFront(e.line);
        }
        for(GraphVertex c : vertexSet){
            //c.circle.toFront();Known issue that toFront() doesn't work
            RenderingsManager.toFront(c.circle);
        }
    }
}
