/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility.StorageClasses;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.Utility.Logger;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Thayer
 */
public class StorableGraph implements Serializable{
    ArrayList<Double> xValues;
    ArrayList<Double> yValues;
    ArrayList<Integer> startNodeIndices;
    ArrayList<Integer> endNodeIndices;
    String name;

    StorableGraph(GraphEntity g){
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        startNodeIndices = new ArrayList<>();
        endNodeIndices = new ArrayList<>();

        Graph graph = g.getGraph();
        ArrayList<GraphVertex> vertices = graph.getVertexSet();
        for(GraphVertex v : vertices){
            xValues.add(v.getX());
            yValues.add(v.getY());
        }
        for(GraphEdge e : graph.getEdgeSet()){
            startNodeIndices.add(vertices.indexOf(e.getStartNode()));
            endNodeIndices.add(vertices.indexOf(e.getEndNode()));
        }
        name = g.getName();
    }

    GraphEntity getGraphEntity(){
        Logger.log("Building graph from stored data.", 1);
        Graph g = new Graph();
        for(int i = 0; i < xValues.size(); i++){
            g.addVertex(xValues.get(i), yValues.get(i));
        }
        Logger.log("Added " + Integer.toString(g.order()) + " vertices.", 2);

        ArrayList<GraphVertex> vertices = g.getVertexSet();
        for(int i = 0; i < startNodeIndices.size(); i++){
            g.drawEdge(vertices.get(startNodeIndices.get(i)),
                      vertices.get(endNodeIndices.get(i)));
        }
        Logger.log("Added " + Integer.toString(g.size()) + " edges.", 2);

        return new GraphEntity(name,g);
    }

    String getName(){
        return name;
    }
}
