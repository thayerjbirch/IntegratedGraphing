/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Utility.OptionsManager;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Thayer
 */
public class OptionsMenu {
    MigLayout window;
    OptionsManager om;
    RenderingsManager rm;

    public OptionsMenu(OptionsManager opt, RenderingsManager rend){
        om = opt;
        rm = rend;
        window = new MigLayout();
        setupLayout();
    }

    private void setupLayout(){
        setupTagGroup();
    }

    private void setupTagGroup(){
        Label tagLabel = new Label("Show vertex names: ");
        ToggleGroup tagGroup = new ToggleGroup();
        RadioButton tagTrue = new RadioButton("True");
        RadioButton tagFalse = new RadioButton("False");
        tagTrue.setToggleGroup(tagGroup);
        tagFalse.setToggleGroup(tagGroup);
        if(om.getShowVertexLabels()){
            tagTrue.setSelected(true);
        }
        else{
            tagFalse.setSelected(false);
        }

//        window.
    }
}
