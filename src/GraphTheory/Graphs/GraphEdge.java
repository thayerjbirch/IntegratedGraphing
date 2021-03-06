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
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private Label lengthLabel;
    double angle;
    
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
        lengthLabel = new Label(Integer.toString(length));
        angle = Math.atan2(startNode.getX() - endNode.getX(), startNode.getY() - endNode.getY());
        //repositionLabel();
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
        setLabel(Integer.toString(x));
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
        angle = Math.atan2(startNode.getX() - endNode.getX(), startNode.getY() - endNode.getY());
        repositionLabel();
    }

    @Override
    public void translate(double x, double y) {
        parent.translate(x, y);
        repositionLabel();
    }

    public GraphVertex getStartNode(){
        return startNode;
    }

    public GraphVertex getEndNode(){
        return endNode;
    }

    public final void repositionLabel(){
        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = line.getEndX();
        double endY = line.getEndY();
        
        double centerX = getCenterX();
        double centerY = getCenterY();

        //The labels are positioned by the corner, and we will be calculating the center of
        //position we want. We are compensating by subtracting half of the label dimensions
        double anchorX = centerX - (lengthLabel.widthProperty().doubleValue() / 2);
        double anchorY = centerY - (lengthLabel.heightProperty().doubleValue() / 2);

        double labelAngle = angle + GuiConstants.ANGLE_INCREMENT;

        //Positions the label on the perpindicular bisector away from the graph's center
        double xPos = anchorX + (Math.sin(labelAngle) * GuiConstants.EDGE_LABEL_RADIUS);
        double xPos2 = anchorX - (Math.sin(labelAngle) * GuiConstants.EDGE_LABEL_RADIUS);

        double yPos = anchorY + (Math.cos(labelAngle) * GuiConstants.EDGE_LABEL_RADIUS);
        double yPos2 = anchorY - (Math.cos(labelAngle) * GuiConstants.EDGE_LABEL_RADIUS);

        if(distFromGraphCenter(xPos,yPos) >= distFromGraphCenter(xPos2,yPos2))
            lengthLabel.relocate(xPos, yPos);
        else
            lengthLabel.relocate(xPos2, yPos2);
    }

    private double distFromGraphCenter(double x, double y){
        double graphX = parent.circle.getCenterX();
        double graphY = parent.circle.getCenterY();
        return Math.sqrt((x - graphX)*(x - graphX) + (y - graphY)*(y - graphY));
    }

    public double getCenterX(){
        return (line.getStartX() + line.getEndX())/2;
    }

    public double getCenterY(){
        return (line.getStartY() + line.getEndY())/2;
    }

    public Label getLabel(){
        return lengthLabel;
    }

    public List<Node> getVisibleElements(){
        OptionsManager optMan = IntegratedGraphing.getHQ().getOptionsManager();
        List<Node> ret;
        ret = new ArrayList<>();
        ret.add(line);

        if(optMan.getShowEdgeLabels())
            ret.add(lengthLabel);
        return ret;
    }

    public void setLabel(String in){
        lengthLabel.setText(in);
    }

    public void setHighlighted(boolean highlighted){
        if(highlighted){
            line.setStroke(Color.RED);
        }
        else{
            line.setStroke(Color.BLACK);
        }
    }

    public boolean isHorizontal(){
        double abAngle = Math.abs(angle);
        return abAngle > Math.PI/4 && abAngle < (3*Math.PI/4);//is between 45 and -45 degrees from horizontal
    }
}
