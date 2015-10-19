/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Graphs;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author Thayer
 */
public interface GraphObject {    
    public class GraphCircle extends Circle{
        GraphObject represents;
        
        public GraphCircle(double size, GraphObject parent){
            super(size);
            represents = parent;
        }
        
        public GraphObject getRepresents(){
            return represents;
        }
    }
    public class GraphLine extends Line{
        GraphObject represents;
        
        public GraphLine(double x1, double y1, double x2, double y2, GraphObject parent){
            super(x1,y1,x2,y2);
            represents = parent;
        }
        
        public GraphObject getRepresents(){
            return represents;
        }        
    }
}
