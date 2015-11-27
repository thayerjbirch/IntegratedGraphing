/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Thayer
 */
public class GraphEdge implements GraphObject,Translatable{
    public GraphLine line;
    protected GraphNode startNode;
    protected GraphNode endNode;
    private Graph parent;
    protected boolean active = false;
    
    public GraphEdge(Graph p, GraphNode start, GraphNode end, Color color){
        parent = p;
        if(p==null||start==null||end==null||color==null)
            throw new IllegalArgumentException("Passed a null value to GraphEdge constructor.");
        startNode = start;
        endNode = end;
        line = new GraphLine(start.getX(),start.getY(),end.getX(),end.getY(),this);
        line.setStrokeWidth(p.strokeSize);
        line.setStroke(color);
        
    }
    
    public GraphEdge(Graph p, GraphNode start, GraphNode end){
        this(p,start,end,Color.BLACK);
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
    
    public void nodeMoved(GraphNode v){
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
}
