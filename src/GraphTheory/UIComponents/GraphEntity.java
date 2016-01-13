/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Input.MouseGestures;
import GraphTheory.IntegratedGraphing;
import GraphTheory.Utility.Logger;
import GraphTheory.Utility.StorageClasses.StorableGraph;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Thayer
 */    
public class GraphEntity{
    String name;
    Graph represents;
    TreeItem<String> tag;

    public GraphEntity(Graph g){
        this(IntegratedGraphing.getHQ().checkName("Graph"),g);
    }

    public GraphEntity(String nameIn, Graph g){
        name = IntegratedGraphing.getHQ().checkName(nameIn);
        represents = g;
        tag = new TreeItem<>(name);
        
        MouseGestures.addGestures(g);  
    }

    public Graph getGraph(){
        return represents;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder(name);
        b.append('\n');
        int[] seq = represents.getDegreeSequence();
        for(int i = 0; i < seq.length; i++)
            b.append(Integer.toString(seq[i])).append(' ');
        b.append('\n');
        int[][] adj = represents.getAdjacencyMatrix();
        for(int i = 0; i < adj.length; i++){
            for(int j = 0; j < adj.length; j++){
                b.append(Integer.toString(adj[i][j])).append(' ');
            }
            b.append('\n');
        }
        return b.toString();
    }

    public void addVertex(double x, double y){
        GraphVertex newVertex = represents.addVertex(x, y);
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
        if(represents.elementOf(u) && represents.elementOf(v)){//checks that both of the vertices are in the graph.
            GraphEdge addedEdge = represents.drawEdge(u, v);
            represents.verticesToFront();
            Logger.log("Edge added to graph " + name);
            return true;
        }
        Logger.log("Adding edge failed. Vertices are members or different graphs.");
        return false;
    }
    
    public void toFront(){
        represents.toFront();
    }

    public GraphEntity getCopy(boolean visible){
        return new StorableGraph(this).getGraphEntity(visible);
    }
}