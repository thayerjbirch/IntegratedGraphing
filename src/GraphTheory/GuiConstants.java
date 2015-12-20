/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory;

import javafx.geometry.Insets;

/**
 *
 * @author Thayer
 */
public class GuiConstants {
    private GuiConstants(){};
    
    public static final double SCENE_HEIGHT = 600;
    public static final double SCENE_WIDTH = 1000;
    public static final double SIDEBAR_WIDTH = 181;
    public static final double DETAILS_CELL_WIDTH = SIDEBAR_WIDTH / 2 - 12;
    public static final double DETAILS_CELL_HEIGHT = 20;
    public static final double DETAILS_ROW_WIDTH = SIDEBAR_WIDTH - 8;
    public static final double DETAILS_ROW_HEIGHT = DETAILS_CELL_HEIGHT + 4;
    public static final double RENDERINGS_HEIGHT = SCENE_HEIGHT;
    public static final double RENDERINGS_WIDTH  = SCENE_WIDTH - SIDEBAR_WIDTH;
    public static final double TOOL_ICON_SIZE = 16;
    public static final double TOOL_MARGIN_SIZE = 4;
    public static final double SM_TOOL_ICON_SIZE = 15;
    public static final double SM_TOOL_MARGIN_SIZE = 2;
    public static final double HIGHLIGHT_BORDER_DEPTH = 35;
    public static final double SEPARATOR_WIDTH = 2;
    public static final Insets DETAILS_LABEL_INSETS = new Insets(0,8,0,8);
}
