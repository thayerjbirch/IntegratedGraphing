/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Mouse.MouseGestures;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Thayer
 */    
public class GraphEntity{
    String name;
    Graph represents;
    TreeItem<String> tag;

    GraphEntity(Graph g){
        this(("Graph " + (GraphManager.size() + 1)),g);
    }

    GraphEntity(String nameIn, Graph g){
        name = nameIn;
        represents = g;
        tag = new TreeItem<>(name);
        
        MouseGestures.addGestures(g);  
        RenderingsManager.addNode(g.graphContents);
    }
    
    public void addVertex(double x, double y){
        GraphVertex newVertex = represents.addVertex(x, y);
        MouseGestures.addGestures(newVertex);
        RenderingsManager.addNode(newVertex.circle);
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public void toFront(){
        represents.toFront();
    }
}