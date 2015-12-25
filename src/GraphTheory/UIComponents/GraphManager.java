/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.UIComponents.SidebarManager;
import GraphTheory.Utility.Logger;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Thayer
 */
public class GraphManager {    
    private final Map<String,GraphEntity> graphsMap;
    private final ArrayList<GraphEntity> graphsList;
    public GraphEntity curGraphEntity;
    
    public GraphManager(){
        graphsMap = new TreeMap<>();
        graphsList = new ArrayList<>();
    }
    
    public ArrayList getGraphs(){
        return graphsList;
    }

    public ArrayList<String> getGraphNames(){
       ArrayList<String> names = new ArrayList<>();
       for(GraphEntity e : graphsList)
           names.add(e.name);
       return names;
    }
    
    public GraphEntity get(int i){
        return graphsList.get(i);
    }

    public GraphEntity get(String name){
        return graphsMap.get(name);
    }

    public GraphEntity getCurrentGraph(){
        return curGraphEntity;
    }
    
    public void addGraph(Graph g){
        addGraph(new GraphEntity(g)); 
    }
     
    public void addGraph(String name, Graph g){
        addGraph(new GraphEntity(name,g));        
    }
    
    private void addGraph(GraphEntity e){
        graphsMap.put(e.name,e);
        graphsList.add(e);
        SidebarManager.addGraph(e);
    }
    
    public boolean addEdge(GraphVertex u, GraphVertex v){
        return curGraphEntity.addEdge(u, v);
    }
    
    public int size(){
        if(graphsList != null)
            return graphsList.size();
        return 0;
    }
    
    public void setCurrentGraph(int index){
        setCurrentGraph(graphsList.get(index));
    }
    
    public void setCurrentGraph(String tag){
        GraphEntity e = graphsMap.get(tag);
        if(e!=null)
            setCurrentGraph(e);
    }
    
    public void setCurrentGraph(Graph g){
        GraphEntity temp = null;
        for(GraphEntity e : graphsList)
            if(e.represents == g){
                temp = e;
                break;
            }
        if(temp!=null){
            setCurrentGraph(temp);
        }
        else
            Logger.log("Could not find an entity containing the specified graph.");
    }
    
    public void setCurrentGraph(GraphEntity e){
        curGraphEntity = e;
        curGraphEntity.toFront();
        SidebarManager.setSelected(e);
        SidebarManager.setDetails(e);
        Logger.log("Selected graph: " + curGraphEntity.getName());
    }
    
    public void addVertex(double x, double y){
        if(curGraphEntity != null){
            curGraphEntity.addVertex(x, y);
        }
        else
            Logger.log("Attempted to add a node to a null graph, operation aborted.");
    }
    
    public boolean removeVertex(GraphVertex v){
        return v.getParent().removeVertex(v);
    }
    
    public boolean removeEdge(GraphEdge e){
        return e.getParent().removeEdge(e);
    }

    public void removeGraph(GraphEntity e){
        RenderingsManager.removeNode(e.getGraph().getCircle());
        
        SidebarManager.removeGraph(e);
        
        graphsList.remove(e);
        graphsMap.remove(e.name);
    }
    
    public boolean hasGraph(String name){
        return graphsMap.containsKey(name);
    }

    public void unionGraphs(GraphEntity g1, GraphEntity g2){
        Logger.log("Merging graphs " + g1.getName() + " and " + g2.getName());
        Graph newG = new Graph();

        Logger.log("Constructing vertex set.",1);
        for(GraphVertex v : g1.getGraph().getVertexSet())
            newG.addVertex(v);
        for(GraphVertex v : g2.getGraph().getVertexSet())
            newG.addVertex(v);
        Logger.log("Added " + newG.order() + " vertices.", 1);

        Logger.log("Constructing edge set.", 1);
        for(GraphEdge e : g1.getGraph().getEdgeSet())
            newG.drawEdge(e);
        for(GraphEdge e : g2.getGraph().getEdgeSet())
            newG.drawEdge(e);
        Logger.log("Added " + newG.size() + " edges.", 1);
        
        removeGraph(g1);
        removeGraph(g2);
        
        Logger.log("Adding the merged graph.", 1);
        newG.updateContentParents();
        addGraph(g1.name + " u " + g2.name, newG);
    }
}
