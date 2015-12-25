/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.IntegratedGraphing;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 *
 * @author Thayer
 */
public class MultiSelectionDialog {
    public static final double COMBO_BOX_WIDTH = 150;

    Dialog<Pair<String,String>> dialog;
    ComboBox pick1,pick2;

    public MultiSelectionDialog(List<String> graphs, String header){
       dialog = new Dialog<>();
       dialog.setTitle("Select Graphs");
       dialog.setHeaderText(header);

       pick1 = new ComboBox();
       pick1.setItems(FXCollections.observableArrayList(graphs));
       pick1.setValue(IntegratedGraphing.getGraphManager().curGraphEntity.getName());
       pick1.setPrefWidth(COMBO_BOX_WIDTH);
       
       pick2 = new ComboBox();
       pick2.setItems(FXCollections.observableArrayList(graphs));
       pick2.setPrefWidth(COMBO_BOX_WIDTH);
       pick2.setValue(graphs.get(0));
       if(pick2.valueProperty().getValue().equals(IntegratedGraphing.getGraphManager().curGraphEntity.getName())){
           pick2.setValue(graphs.get(1));
       }

       HBox holder = new HBox(10);//parameter is spacing
       holder.getChildren().addAll(pick1,pick2);

       dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
       dialog.getDialogPane().setContent(holder);

       dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                    return new Pair<>((String)pick1.valueProperty().getValue(), 
                            (String)pick2.valueProperty().getValue());
                }
                return null;
            });
    }

    public Optional<Pair<String,String>> showAndWait(){
        return dialog.showAndWait();
    }
}
