/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Thayer
 */
public class ImageToggleButton extends ToggleButton{
    private final ImageView iv;

    public ImageToggleButton(Image image){
        super();
        iv = new ImageView();
        iv.setImage(image);
        iv.maxHeight(this.getHeight());
        iv.maxWidth(this.getWidth());
        this.getChildren().add(iv);
        iv.toFront();
    }

    public void setPrefHeight(Double h){
        super.setPrefHeight(h);
        iv.maxHeight(h);
    }

    public void setPrefWidth(Double w){
        super.setPrefWidth(w);
        iv.maxWidth(w);
    }
}
