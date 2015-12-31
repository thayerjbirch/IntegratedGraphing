/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 *
 * @author Thayer
 */
public class GraphEdge implements GraphObject,Translatable{
    public GraphLine line;
    protected GraphVertex startNode;
    protected GraphVertex endNode;
    private Graph parent;
    protected boolean active = false;
    private int length;
    
    public GraphEdge(Graph p, GraphVertex start, GraphVertex end, Color color, int lengthIn){
        parent = p;
        if(p==null||start==null||end==null||color==null)
            throw new IllegalArgumentException("Passed a null value to GraphEdge constructor.");
        startNode = start;
        endNode = end;
        line = new GraphLine(start.getX(),start.getY(),end.getX(),end.getY(),this);
        line.setStrokeWidth(p.strokeSize);
        line.setStroke(color);
        length = lengthIn;
    }
    
    public GraphEdge(Graph p, GraphVertex start, GraphVertex end){
        this(p,start,end,1);
    }
    
    public GraphEdge(Graph p, GraphVertex start, GraphVertex end, Color color){
        this(p,start,end,color,1);
    }

    public GraphEdge(Graph p, GraphVertex start, GraphVertex end, int lengthIn){
        this(p,start,end,Color.BLACK,lengthIn);
    }
    
    public Graph getParent(){
        return parent;
    }

    public GraphLine getLine(){
        return line;
    }

    protected void setParent(Graph g){
        parent = g;
    }

    public void setLength(int x){
        length = x;
    }

    public int getLength(){
        return length;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof GraphEdge))
            return false;
        GraphEdge e = (GraphEdge) o;
        if(e.startNode == this.startNode){
            if(e.endNode == this.endNode)
                return true;
        } else if(e.startNode == this.endNode){
            if(e.endNode == this.startNode)
                return true;
        }
        return false;
    }
    
    public void vertexMoved(GraphVertex v){
        if(v==startNode){
            line.setStartX(v.getX());
            line.setStartY(v.getY());
        } else if(v==endNode){
            line.setEndX(v.getX());
            line.setEndY(v.getY());
        }
    }

    @Override
    public void translate(double x, double y) {
        parent.translate(x, y);
    }

    public GraphVertex getStartNode(){
        return startNode;
    }

    public GraphVertex getEndNode(){
        return endNode;
    }
}
