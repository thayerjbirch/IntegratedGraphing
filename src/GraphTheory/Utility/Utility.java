/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import GraphTheory.GuiConstants;
import GraphTheory.IntegratedGraphing;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Thayer
 */
public final class Utility {
    public static Image loadImage(String imageName){
        String path = new String(IntegratedGraphing.imageDirectory + imageName);
        File imgFile = new File(path);
        if(imgFile.exists())
            return new Image(imgFile.toURI().toString());
        else{
            JOptionPane.showMessageDialog(null, "<html>Error<br>Missing images</html>" ,
               "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static ImageView loadView(Image content){
        ImageView view = new ImageView(content);
        view.setFitWidth(GuiConstants.TOOL_ICON_SIZE);
        view.setFitHeight(GuiConstants.TOOL_ICON_SIZE);
        view.setPreserveRatio(true);
        return view;
    }
    
    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(100)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void ensureAToggle(MouseEvent e){
        ToggleButton srcButton = (ToggleButton)e.getSource();
        if(srcButton.getToggleGroup().getSelectedToggle() == null)
            srcButton.setSelected(true);
    }
}
