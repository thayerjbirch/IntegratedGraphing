/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Input;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Graphs.GraphObject.GraphCircle;
import GraphTheory.Graphs.GraphObject.GraphLine;
import GraphTheory.Graphs.Translatable;
import static GraphTheory.Input.ToolManager.currentTool;
import GraphTheory.IntegratedGraphing;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.UIComponents.RenderingsManager;
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
        g.circle.setOnMousePressed(vertexOnMousePressedEventHandler);
        g.circle.setOnMouseDragged(vertexOnMouseDraggedEventHandler);
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
            case DELETE:
                IntegratedGraphing.getGraphManager().removeEdge((GraphEdge) ((GraphLine)event.getSource()).getRepresents());
                break;
        }       
    };
    
    static EventHandler<MouseEvent> vertexOnMousePressedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerPressed(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                //RenderingsManager.setSelected( (GraphVertex) ((GraphCircle)event.getSource()).getRepresents());
                break;
            case EDGE:
                if(RenderingsManager.getSelected() == null){
                    RenderingsManager.setSelected( (GraphVertex) ((GraphCircle)event.getSource()).getRepresents());
                    Logger.log("Set the starting vertex.");
                }
                else{
                    if(IntegratedGraphing.getGraphManager().addEdge(RenderingsManager.getSelected(),
                                            (GraphVertex) ((GraphCircle)event.getSource()).getRepresents())){
                        //If the adding the edge succeeds. It will fail if the two vertices are
                        //in distinct Graphs
                        RenderingsManager.setSelected(null);
                    }
                }
                break;
            case DELETE:
                IntegratedGraphing.getGraphManager().removeVertex( (GraphVertex) ((GraphCircle)event.getSource()).getRepresents());
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
    
    static EventHandler<MouseEvent> vertexOnMouseDraggedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case POINTER:
                handlePointerDragged(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                break;
        }        
    };
    
    static EventHandler<MouseEvent> paneOnMouseClickedEventHandler = (MouseEvent event) -> {
        switch(currentTool){
            case VERTEX:
                IntegratedGraphing.getGraphManager().addVertex(event.getX(), event.getY());
                Logger.log("Vertex added at (" + event.getX() + "," + event.getY() + ").");
                break;
        }        
    };
    
    protected static void handlePointerPressed(MouseEvent event, Translatable t){
        oldX = event.getSceneX();
        oldY = event.getSceneY();
        RenderingsManager.removeGlow();
        if(t instanceof Graph)
            IntegratedGraphing.getGraphManager().setCurrentGraph((Graph)t);
        else if(t instanceof GraphVertex)
            IntegratedGraphing.getGraphManager().setCurrentGraph(((GraphVertex)t).getParent());
        else if(t instanceof GraphEdge)
            IntegratedGraphing.getGraphManager().setCurrentGraph(((GraphEdge)t).getParent());
    }
    
    protected static void handlePointerDragged(MouseEvent event, Translatable t){
        t.translate(event.getSceneX() - oldX, event.getSceneY() - oldY);
        oldX = event.getSceneX();
        oldY = event.getSceneY();
    }
    
        static double orgSceneX, orgSceneY;
        static double orgTranslateX, orgTranslateY;
}

