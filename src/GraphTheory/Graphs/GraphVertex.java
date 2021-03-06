/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import GraphTheory.GuiConstants;
import GraphTheory.IntegratedGraphing;
import GraphTheory.Utility.OptionsManager;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author Thayer
 */
public class GraphVertex implements GraphObject,Translatable{
    public GraphCircle circle;
    private Graph parent;
    private ArrayList<GraphVertex> adjacentTo;
    private ArrayList<GraphEdge> edges;
    private SimpleStringProperty name;
    private Label nameLabel;

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
        return adjacentTo.size();
    }

    public void addAdjacent(GraphVertex v){
        adjacentTo.add(v);
    }

    public void removeAdjacent(GraphVertex v){
        adjacentTo.remove(v);
    }
    
    public Graph getParent(){
        return parent;
    }

    protected void setParent(Graph newParent){
        parent = newParent;
    }

    public ArrayList<GraphVertex> getAdjacentTo() {
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
            e.vertexMoved(this);
        }
    }

    public GraphVertex(Graph g, double x, double y, Color color, String name){
        parent = g;
        circle = new GraphCircle(g.vertexSize,this);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(color);
        circle.setStroke(color);
        adjacentTo = new ArrayList();
        edges = new ArrayList();

        this.name = new SimpleStringProperty(name);
        nameLabel = new Label();
        nameLabel.textProperty().bind(this.name);
        repositionLabel();
    }
    
    public GraphVertex(Graph g, double x, double y, Color color){
        this(g,x,y,color, g.getVertexName());
    }
    
    public GraphVertex(Graph g, double x, double y){
        this(g,x,y,Color.BLACK);
    }
    
    public BitSet adjecentTo(){
        BitSet adj = new BitSet(parent.order());
        ArrayList<GraphVertex> vertices = parent.getVertexSet();
        for(int i = 0; i < vertices.size(); i++)
            if(adjacentTo.contains(vertices.get(i)))
                adj.set(i);
        return adj;
    }

    @Override
    public void translate(double x, double y) {
        circle.translate(x, y);
        repositionLabel();
        for(GraphEdge e : edges)
            e.vertexMoved(this);
        parent.recenterCircle();
    }

    public final void repositionLabel(){
        double parentX = parent.circle.getCenterX();
        double parentY = parent.circle.getCenterY();
        double localX = getX();
        double localY = getY();

        double angle = Math.atan2(localY - parentY, localX - parentX);
        double xPos = Math.cos(angle) * GuiConstants.LABEL_RADIUS + localX
                - (nameLabel.getWidth() / 2);
        double yPos = Math.sin(angle) * GuiConstants.LABEL_RADIUS + localY
                - (nameLabel.getHeight() / 2);
        nameLabel.relocate(xPos, yPos);
//        IntegratedGraphing.getHQ().getRenderingsMgr().ensureValidPosition(nameLabel);
    }

    public Label getLabel(){
        return nameLabel;
    }

    public List<Node> getVisibleElements(){
        OptionsManager optMan = IntegratedGraphing.getHQ().getOptionsManager();
        List<Node> ret;
        ret = new ArrayList<>();
        ret.add(circle);

        if(optMan.getShowVertexLabels())
            ret.add(nameLabel);
        return ret;
    }

    public void setLabel(String in){
        nameLabel.setText(in);
    }
}
