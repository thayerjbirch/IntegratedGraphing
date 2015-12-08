/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Mouse;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Graphs.GraphObject.GraphCircle;
import GraphTheory.Graphs.GraphObject.GraphLine;
import GraphTheory.Graphs.Translatable;
import static GraphTheory.Mouse.ToolManager.currentTool;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.Utility.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Thayer
 */
public final class MouseGestures {
    private MouseGestures(){      
    }
    
    static double oldX;
    static double oldY;
    
    public static void addGestures(Graph g){
        g.circle.setOnMousePressed(graphOnMousePressedEventHandler);
        g.circle.setOnMouseDragged(graphOnMouseDraggedEventHandler);
    }
    
    public static void addGestures(GraphVertex g){
        g.circle.setOnMousePressed(graphOnMousePressedEventHandler);
        g.circle.setOnMouseDragged(graphOnMouseDraggedEventHandler);
    }
    
    public static void addGestures(GraphEdge g){
        g.line.setOnMousePressed(edgeOnMousePressedEventHandler);
        g.line.setOnMouseDragged(edgeOnMouseDraggedEventHandler);
    }
    
    public static void addGestures(Pane p){
        p.setOnMouseClicked(paneOnMouseClickedEventHandler);
    }
    
    static EventHandler<MouseEvent> graphOnMousePressedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerPressed(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                break;
        }        
    };
    static EventHandler<MouseEvent> edgeOnMousePressedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerPressed(event, (Translatable) ((GraphLine)event.getSource()).getRepresents());
                break;
        }        
    };
    
    static EventHandler<MouseEvent> nodeOnMousePressedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerPressed(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                break;
        }
    };
    static EventHandler<MouseEvent> graphOnMouseDraggedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerDragged(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                break;
        }        
    };
    static EventHandler<MouseEvent> edgeOnMouseDraggedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerDragged(event, (Translatable) ((GraphLine)event.getSource()).getRepresents());
                break;
        }        
    };
    
    static EventHandler<MouseEvent> paneOnMouseClickedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case VERTEX:
                GraphManager.addVertex(event.getX(), event.getY());
                Logger.log("Vertex added at (" + event.getX() + "," + event.getY() + ").");
                break;
        }        
    };
    
    protected static void handlePointerPressed(MouseEvent event, Translatable t){
        oldX = event.getSceneX();
        oldY = event.getSceneY();
        if(t instanceof Graph)
            GraphManager.setCurrentGraph((Graph)t);
        else if(t instanceof GraphVertex)
            GraphManager.setCurrentGraph(((GraphVertex)t).getParent());
        else if(t instanceof GraphEdge)
            GraphManager.setCurrentGraph(((GraphEdge)t).getParent());
    }
    
    protected static void handlePointerDragged(MouseEvent event, Translatable t){
        t.translate(event.getSceneX() - oldX, event.getSceneY() - oldY);
        oldX = event.getSceneX();
        oldY = event.getSceneY();
    }
    
        static double orgSceneX, orgSceneY;
        static double orgTranslateX, orgTranslateY;
}

