/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.GuiConstants;
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

    public DetailsRow(String left, String right, String ttip){
        leftField = new Label(left);
        rightField = new Label(right);
        Tooltip tipIn = new Tooltip(ttip);
        leftField.setTooltip(tipIn);
        rightField.setTooltip(tipIn);
//        leftField.setPrefWidth(GuiConstants.DETAILS_CELL_WIDTH);
//        rightField.setPrefWidth(GuiConstants.DETAILS_CELL_WIDTH);
        row.setMaxHeight(GuiConstants.DETAILS_ROW_HEIGHT);
        row.setMaxWidth(GuiConstants.SIDEBAR_WIDTH);
        row.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        row.getChildren().addAll(leftField,rightField);
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
