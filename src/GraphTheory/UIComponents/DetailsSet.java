/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphObject;
import GraphTheory.Graphs.GraphVertex;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 *
 * @author Thayer
 */
public class DetailsSet {
    public ArrayList<DetailsRow> dets = new ArrayList<>();
    
    public static enum setType{
        GRAPH,VERTEX,EDGE
    }

    public DetailsSet(Graph g){
        dets.add(new DetailsRow(("Order"), g.orderProperty, "The number of vertices in the graph."));
        dets.add(new DetailsRow(("Size"), g.sizeProperty, "The number of edges contained in the graph."));
        dets.add(new DetailsRow(("Density"), g.densityProperty, "The proportion of present edges compared to "
                + "the number\n of edges in a complete graph on the same number of vertices"));
    }

    public void addDetail(DetailsRow newDetail){
        dets.add(newDetail);
    }

    public void changeTarget(Graph g){
       dets.get(0).setProperty(g.orderProperty);
       dets.get(1).setProperty(g.sizeProperty);
       dets.get(2).setProperty(g.densityProperty);
    }

    public ArrayList<Node> getRows(){
        ArrayList<Node> ret = new ArrayList<>();
        for(DetailsRow r : dets)
            ret.add(r.row);
        return ret;
    }

}