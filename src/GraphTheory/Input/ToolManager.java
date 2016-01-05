/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Input;

import GraphTheory.IntegratedGraphing;
import GraphTheory.Utility.Logger;
import GraphTheory.Utility.Utility;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author Thayer
 */
public class ToolManager {
    private static final ToggleGroup contents = new ToggleGroup();
    
    public static HBox setupToolManager(){
        HBox toolbar = new HBox();

        toolbar.getChildren().add(setupFileTools());

        toolbar.getChildren().add(new Separator());
        toolbar.getChildren().add(setupDrawingTools());

        return toolbar;
    }

    private static HBox setupDrawingTools(){
        ToggleButton pointerButton = Utility.setupToggleButton("PointerTool.png",pointerButtonPressed,
                                                 "Manipulates the position of graphs, edges, and vertices.");
        pointerButton.setToggleGroup(contents);
        
        ToggleButton vertexButton = Utility.setupToggleButton("VertexTool.png", vertexButtonPressed,
                                                "Create a new vertex for the current graph.");
        vertexButton.setToggleGroup(contents);
        
        ToggleButton edgeButton = Utility.setupToggleButton("EdgeTool.png", edgeButtonPressed,
                                              "Create a new edge for the current graph.");
        edgeButton.setToggleGroup(contents);
        
        ToggleButton deleteButton = Utility.setupToggleButton("DeleteTool.png", deleteButtonPressed,
                                                "Delete an edge or vertex.");
        deleteButton.setToggleGroup(contents);
        
        pointerButton.setSelected(true);
        return new HBox(pointerButton, vertexButton, edgeButton, deleteButton);
    }

    private static HBox setupFileTools(){
        Button newButton = Utility.setupButton("NewIcon.png", newButtonPressed, 
                                               "Clear current work and create a blank workspace");

        Button saveButton = Utility.setupButton("SaveIcon.png", saveButtonPressed,
                                                "Save the current workspace to a file.");

        return new HBox(newButton, saveButton);
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

    static EventHandler<MouseEvent> newButtonPressed = (MouseEvent event) -> {
        IntegratedGraphing.getHQ().newWorkspace();
    };

    static EventHandler<MouseEvent> saveButtonPressed = (MouseEvent event) -> {
        IntegratedGraphing.getHQ().saveAsToFile();
    };
    
    public static Tool currentTool = Tool.POINTER;   
    
    public enum Tool {
        POINTER, VERTEX, EDGE, DELETE
    } 
}
