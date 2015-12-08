/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Mouse.MouseGestures;
import GraphTheory.Utility.Logger;
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
        RenderingsManager.addNode(newVertex.circle);
    }
    
    public boolean removeVertex(GraphVertex v){
        if(represents.removeVertex(v)){
            Logger.log("Deleted vertex from graph " + name);
            return true;
        }
        else{
            Logger.log("Failed to delete vertex.");
            return false;
        }
    }
    
    public boolean removeEdge(GraphEdge e){
        if(represents.removeEdge(e)){
            Logger.log("Deleted edge from graph " + name);
            return true;
        }
        else{
            Logger.log("Failed to delete edge.");
            return false;
        }
    }
    
    public boolean addEdge(GraphVertex u, GraphVertex v){
        if(represents.elementOf(u) && represents.elementOf(v)){
            GraphEdge newEdge = represents.addEdge(u, v);
            RenderingsManager.addNode(newEdge.line);
            represents.verticesToFront();
            Logger.log("Edge added to graph " + name);
            return true;
        }
        Logger.log("Adding edge failed.");
        return false;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public void toFront(){
        represents.toFront();
    }
}