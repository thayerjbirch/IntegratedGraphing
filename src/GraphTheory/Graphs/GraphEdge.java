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
public class GraphEdge implements GraphObject{
    public GraphLine line;
    protected GraphNode startNode;
    protected GraphNode endNode;
    
    public GraphEdge(Graph p, GraphNode start, GraphNode end, Color color){
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
    
    public void nodeMoved(GraphNode v){
        if(v==startNode){
            line.setStartX(v.getX());
            line.setStartY(v.getY());
        } else if(v==endNode){
            line.setEndX(v.getX());
            line.setEndY(v.getY());
        }
    }
}
