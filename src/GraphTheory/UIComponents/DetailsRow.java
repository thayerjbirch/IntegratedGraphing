/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.GuiConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 *
 * @author Thayer
 */
public class DetailsRow{
    HBox row = new HBox();
    Label leftField;
    Label rightField;
    Tooltip tooltip;

    public DetailsRow(String left, SimpleStringProperty right, String ttip){
        Tooltip tipIn = new Tooltip(ttip);
        
        leftField = new Label(left);
        leftField.setTooltip(tipIn);
        leftField.setMinHeight(GuiConstants.DETAILS_CELL_HEIGHT);
        leftField.setMinWidth(GuiConstants.DETAILS_CELL_WIDTH);
        leftField.setStyle("-fx-border-color:grey; -fx-background-color: white;");
        leftField.setPadding(GuiConstants.DETAILS_LABEL_INSETS);
        
        rightField = new Label();//right);
        rightField.textProperty().bind(right);
        rightField.setTooltip(tipIn);
        rightField.setMinHeight(GuiConstants.DETAILS_CELL_HEIGHT);
        rightField.setMinWidth(GuiConstants.DETAILS_CELL_WIDTH);
        rightField.setAlignment(Pos.CENTER_RIGHT);
        rightField.setStyle("-fx-border-color:grey; -fx-background-color: white;");
        rightField.setPadding(GuiConstants.DETAILS_LABEL_INSETS);
        
        row.setMaxHeight(GuiConstants.DETAILS_ROW_HEIGHT);
        row.setMaxWidth(GuiConstants.DETAILS_ROW_WIDTH);
        row.setPrefWidth(GuiConstants.DETAILS_ROW_WIDTH);

        row.getChildren().addAll(leftField,rightField);
    }

    public void setProperty(SimpleStringProperty s){
        rightField.textProperty().bind(s);
    }

    public HBox getRow(){
        return row;
    }

    public String getRightText(){
        return rightField.getText();
    }

    public String getLeftText(){
        return leftField.getText();
    }

    public void setRightText(String textIn){
        rightField.setText(textIn);
    }
}
