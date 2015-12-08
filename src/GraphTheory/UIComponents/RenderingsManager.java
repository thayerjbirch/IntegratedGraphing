/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.GuiConstants;
import GraphTheory.Mouse.MouseGestures;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author Thayer
 */
public class RenderingsManager {
    private static Pane renderings;
    
    private RenderingsManager(){};
    
    public static Pane setupRenderings(){
        renderings = new Pane();
        renderings.prefHeight(GuiConstants.RENDERINGS_HEIGHT);
        renderings.prefWidth(GuiConstants.RENDERINGS_WIDTH);
        renderings.setStyle("-fx-background-color: white;");
        MouseGestures.addGestures(renderings);
        
        return renderings;
    }
    
    public static void addNode(Node nodeIn){
        renderings.getChildren().add(nodeIn);
    }
    
    public static void removeNode(Node nodeOut){
        renderings.getChildren().remove(nodeOut);
    }
    
    //The normal toFront method doesnt work correctly(know issue of javafx), this is a workaround
    public static void toFront(Node node){
        removeNode(node);
        addNode(node);
    }
}
