/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Thayer
 */
public class GraphNode implements GraphObject,Translatable{
    private int degree;
    public GraphCircle circle;
    private Graph parent;
    private ArrayList<GraphNode> adjacentTo = new ArrayList();
    private ArrayList<GraphEdge> edges = new ArrayList();

    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }
    
    public void addEdge(GraphEdge e){
        edges.add(e);
    }
    
    public void removeEdge(GraphEdge e){
        edges.remove(e);
    }

    public int getDegree() {
        return degree;
    }

    public ArrayList<GraphNode> getAdjacentTo() {
        return adjacentTo;
    }    
    
    public double getX(){
        return circle.getCenterX();
    }
    
    public void setX(double x){
        circle.setCenterX(x);
        updateEdges();
    }
    
    public double getY(){
        return circle.getCenterY();
    }
    
    public void setY(double y){
        circle.setCenterY(y);
        updateEdges();
    }
    
    public void setPosition(Point2D p){
        circle.setCenterX(p.getX());
        circle.setCenterY(p.getY());
        updateEdges();
    }
    
    public void updateEdges(){
        for(GraphEdge e : edges){
            e.nodeMoved(this);
        }
    }
    
    public GraphNode(Graph g, double x, double y, Color color){
        degree = 0;
        parent = g;
        circle = new GraphCircle(g.nodeSize,this);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(color);
        circle.setStroke(color);
    }
    
    public GraphNode(Graph g, double x, double y){
        this(g,x,y,Color.BLACK);
    }

    @Override
    public void translate(double x, double y) {
        circle.translate(x, y);
        for(GraphEdge e : edges)
            e.nodeMoved(this);
        parent.recenterCircle();
    }
}
