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
    private static Map<String,GraphEntity> graphsMap = new TreeMap<>();
    private static ArrayList<GraphEntity> graphsList = new ArrayList<>();
    public static GraphEntity curGraphEntity;
    
    private GraphManager(){};
    
    public static ArrayList getGraphs(){
        return graphsList;
    }

    public static ArrayList<String> getGraphNames(){
       ArrayList<String> names = new ArrayList<>();
       for(GraphEntity e : graphsList)
           names.add(e.name);
       return names;
    }
    
    public static GraphEntity get(int i){
        return graphsList.get(i);
    }

    public static GraphEntity get(String name){
        return graphsMap.get(name);
    }

    public static GraphEntity getCurrentGraph(){
        return curGraphEntity;
    }
    
    public static void addGraph(Graph g){
        addGraph(new GraphEntity(g)); 
    }
     
    public static void addGraph(String name, Graph g){
        addGraph(new GraphEntity(name,g));        
    }
    
    private static void addGraph(GraphEntity e){
        graphsMap.put(e.name,e);
        graphsList.add(e);
        SidebarManager.addGraph(e);
    }
    
    public static boolean addEdge(GraphVertex u, GraphVertex v){
        return curGraphEntity.addEdge(u, v);
    }
    
    public static int size(){
        if(graphsList != null)
            return graphsList.size();
        return 0;
    }
    
    public static void setCurrentGraph(int index){
        setCurrentGraph(graphsList.get(index));
    }
    
    public static void setCurrentGraph(String tag){
        GraphEntity e = graphsMap.get(tag);
        if(e!=null)
            setCurrentGraph(e);
    }
    
    public static void setCurrentGraph(Graph g){
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
    
    public static void setCurrentGraph(GraphEntity e){
        curGraphEntity = e;
        curGraphEntity.toFront();
        SidebarManager.setSelected(e);
        SidebarManager.setDetails(e);
        Logger.log("Selected graph: " + curGraphEntity.getName());
    }
    
    public static void addVertex(double x, double y){
        if(curGraphEntity != null){
            curGraphEntity.addVertex(x, y);
        }
        else
            Logger.log("Attempted to add a node to a null graph, operation aborted.");
    }
    
    public static boolean removeVertex(GraphVertex v){
        return curGraphEntity.removeVertex(v);
    }
    
    public static boolean removeEdge(GraphEdge e){
        return curGraphEntity.removeEdge(e);
    }
    
    public static boolean hasGraph(String name){
        return graphsMap.containsKey(name);
    }
}
