/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility.StorageClasses;

import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.Utility.StorageClasses.StorableGraph;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Thayer
 */
public class GraphList implements Serializable{
    ArrayList<StorableGraph> list;

    public GraphList(ArrayList<GraphEntity> listIn){
        list = new ArrayList<>();
        for(GraphEntity e : listIn){
            list.add(new StorableGraph(e));
        }
    }

    public ArrayList<GraphEntity> getList(){
        ArrayList<GraphEntity> entities = new ArrayList<>();
        for(StorableGraph sg : list){
            entities.add(sg.getGraphEntity());
        }
        return entities;
    }
}
