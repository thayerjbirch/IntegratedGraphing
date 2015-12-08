/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Mouse;

import GraphTheory.Utility.Logger;
import GraphTheory.Utility.Utility;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author Thayer
 */
public class ToolManager {
    private static final ToggleGroup contents = new ToggleGroup();
    
    public static HBox setupToolManager(){
        ToggleButton pointerButton = setupButton("PointerTool.png",pointerButtonPressed,
                                                 "Manipulates the position of graphs, edges, and vertices.");
        
        ToggleButton vertexButton = setupButton("VertexTool.png", vertexButtonPressed,
                                                "Create a new vertex for the current graph.");
        
        ToggleButton edgeButton = setupButton("EdgeTool.png", edgeButtonPressed,
                                              "Create a new edge for the current graph.");
        
        ToggleButton deleteButton = setupButton("DeleteTool.png", deleteButtonPressed,
                                                "Delete an edge or vertex.");
        
        pointerButton.setSelected(true);
        return new HBox(pointerButton, vertexButton, edgeButton, deleteButton);
    }
    
    static ToggleButton setupButton(String imgName, EventHandler<MouseEvent> clickHandler,
                                    String tooltipText){      
        ToggleButton button = new ToggleButton(null, Utility.loadView(Utility.loadImage(imgName)));
        button.setOnMouseClicked(clickHandler);
        button.setToggleGroup(contents);
        
        Tooltip tooltip = new Tooltip(tooltipText);
        Utility.hackTooltipStartTiming(tooltip);
        button.setTooltip(tooltip);
        
        return button;        
    }
    
    private static void setTool(Tool t, MouseEvent e){
        currentTool = t;        
        Utility.ensureAToggle(e);
    }
    
    static EventHandler<MouseEvent> pointerButtonPressed = (MouseEvent event) -> {
        setTool(Tool.POINTER, event);
        Logger.log("Tool changed to the pointer tool.");
    };
    
    static EventHandler<MouseEvent> vertexButtonPressed = (MouseEvent event) -> {
        setTool(Tool.VERTEX, event);
        Logger.log("Tool changed to the create vertex tool.");
    };
    
    static EventHandler<MouseEvent> edgeButtonPressed = (MouseEvent event) -> {
        setTool(Tool.EDGE, event);
        Logger.log("Tool changed to the create edge tool.");
    };
    
    static EventHandler<MouseEvent> deleteButtonPressed = (MouseEvent event) -> {
        setTool(Tool.DELETE, event);
        Logger.log("Tool changed to the delete object tool");
    };
    
    public static Tool currentTool = Tool.POINTER;   
    
    public enum Tool {
        POINTER, VERTEX, EDGE, DELETE
    } 
}
