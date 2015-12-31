/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.GraphVertex;
import GraphTheory.GuiConstants;
import GraphTheory.Input.MouseGestures;
import GraphTheory.Utility.Logger;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Thayer
 */
public class RenderingsManager {
    private final Pane renderings;
    private final DropShadow borderGlow;
    private GraphVertex selectedVertex;
    
    
    public RenderingsManager(){
        renderings = new Pane();
        renderings.prefHeight(GuiConstants.RENDERINGS_HEIGHT);
        renderings.prefWidth(GuiConstants.RENDERINGS_WIDTH);
        renderings.setStyle("-fx-background-color: white;");
        MouseGestures.addGestures(renderings);
        
        borderGlow = new DropShadow(GuiConstants.HIGHLIGHT_BORDER_DEPTH,Color.GOLD);
        borderGlow.setSpread(.75);
//        borderGlow.setOffsetY(0f);
//        borderGlow.setOffsetX(0f);
//        borderGlow.setColor(Color.LIGHTGOLDENRODYELLOW);
//        borderGlow.setWidth(GuiConstants.HIGHLIGHT_BORDER_DEPTH);
//        borderGlow.setHeight(GuiConstants.HIGHLIGHT_BORDER_DEPTH);
    }
    
    public void setSelected(GraphVertex nodeIn){
        if(selectedVertex!=null){
            selectedVertex.circle.setEffect(null);
        }
        if(nodeIn!=null){
            selectedVertex = nodeIn;
            selectedVertex.circle.setEffect(borderGlow);
        } else {
            selectedVertex = null;
        }
    }

    public Pane getPane(){
        return renderings;
    }
    
    public GraphVertex getSelected(){
        return selectedVertex;
    }
    
    public void removeGlow(){
        if(selectedVertex!=null)
            selectedVertex.circle.setEffect(null);
        selectedVertex = null;
    }
    
    public void addNode(Node nodeIn){
        renderings.getChildren().add(nodeIn);
    }
    
    public void removeNode(Node nodeOut){
        if(!renderings.getChildren().contains(nodeOut)){
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            Logger.log("Attempted to remove a non-existant node: " + nodeOut.toString());
            return;
        }
        renderings.getChildren().remove(nodeOut);
    }
    
    //The normal toFront method doesnt work correctly(know issue of javafx), this is a workaround
    public void toFront(Node node){
        removeNode(node);
        addNode(node);
    }
}
