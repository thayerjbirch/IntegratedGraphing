/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;

/**
 *
 * @author Thayer
 */
public class DetailsSet {
    public ArrayList<DetailsRow> dets = new ArrayList<>();
    
    private static SimpleStringProperty defaultText;
    
    public static enum setType{
        GRAPH,VERTEX,EDGE
    }

    public DetailsSet(){
        dets = new ArrayList<>();
        defaultText = new SimpleStringProperty();
        defaultText.setValue("Not found.");
        dets.add(new DetailsRow(("Order"), defaultText, "The number of vertices in the graph."));
        dets.add(new DetailsRow(("Size"), defaultText, "The number of edges contained in the graph."));
        dets.add(new DetailsRow(("Density"), defaultText, "The proportion of present edges compared to "
                + "the number\n of edges in a complete graph on the same number of vertices"));
    }

    public DetailsSet(Graph g){
        this();
        changeTarget(g);
    }

    public void addDetail(DetailsRow newDetail){
        dets.add(newDetail);
    }

    public final void changeTarget(Graph g){
       dets.get(0).setProperty(g.orderProperty);
       dets.get(1).setProperty(g.sizeProperty);
       dets.get(2).setProperty(g.densityProperty);
    }

    public final void setEmpty(){
        for(DetailsRow d : dets){
            d.setProperty(defaultText);
        }
    }

    public ArrayList<Node> getRows(){
        ArrayList<Node> ret = new ArrayList<>();
        for(DetailsRow r : dets)
            ret.add(r.row);
        return ret;
    }

}