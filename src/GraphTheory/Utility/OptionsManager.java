/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import java.io.Serializable;

/**
 *
 * @author Thayer
 */
public class OptionsManager implements Serializable{
    private boolean showVertexLabels;
    private boolean showEdgeLabels;

    public boolean getShowEdgeLabels() {
        return showEdgeLabels;
    }

    public void setShowEdgeLabels(boolean showEdgeLabels) {
        this.showEdgeLabels = showEdgeLabels;
    }
    private final transient Headquarters hq; //transient excludes from serializing

    public OptionsManager(Headquarters h){
        setToDefaults();
        hq = h;
    }

    public void setShowVertexLabels(boolean val){
        showVertexLabels = val;
    }

    public boolean getShowVertexLabels(){
        return showVertexLabels;
    }

    public final void setToDefaults(){
        showVertexLabels = false;
        showEdgeLabels = false;
    }
}
