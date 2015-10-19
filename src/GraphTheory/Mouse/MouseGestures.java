/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Mouse;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphNode;
import GraphTheory.Graphs.GraphObject.GraphCircle;
import GraphTheory.Graphs.Translatable;
import static GraphTheory.Mouse.Utility.currentTool;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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
    
    static EventHandler<MouseEvent> graphOnMousePressedEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            switch(currentTool){
                case POINTER:
                    handlePointerPressed(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                    break;
            }
            
        System.out.println("here");
        }        
    };
    static EventHandler<MouseEvent> nodeOnMousePressedEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            switch(currentTool){
                case POINTER:
                    //handlePointerPressed(event, (GraphNode)event.getSource());
                    break;
            }
        }
    };
    static EventHandler<MouseEvent> graphOnMouseDraggedEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            switch(currentTool){
                case POINTER:
                    handlePointerDragged(event, (Translatable) ((GraphCircle)event.getSource()).getRepresents());
                    break;
            }
        }        
    };
    
    protected static void handlePointerPressed(MouseEvent event, Translatable t){
        oldX = event.getSceneX();
        oldY = event.getSceneY();
    }
    
    protected static void handlePointerDragged(MouseEvent event, Translatable t){
        t.translate(event.getSceneX() - oldX, event.getSceneY() - oldY);
    }
}

