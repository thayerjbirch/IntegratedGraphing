/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.Graphs.Graph;
import GraphTheory.Input.ToolManager;
import GraphTheory.IntegratedGraphing;
import GraphTheory.Utility.Logger;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 *
 * @author Thayer
 */
public class MenuManager {
    private static ToolBar toolbar;
    
    private MenuManager(){};
    
    public static VBox createMenus(Scene s){
        Logger.log("Setting up the main menu bar.",2);
        
        MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuGraphs = new Menu("Graphs");
        Menu menuOptions = new Menu("Options");

        mainMenu.getMenus().addAll(menuFile,menuGraphs,menuOptions);
        mainMenu.prefWidthProperty().bind(s.widthProperty());
        
        setupMenu(menuFile);
        setupGraphs(menuGraphs);

        Logger.log("Setting up the toolbar.",2);
        toolbar = new ToolBar();
        toolbar.getItems().add(ToolManager.setupToolManager());

        VBox menuContainer = new VBox(mainMenu,toolbar);
        Logger.log("Setting up menus complete.", 2);
        return menuContainer;
    }

    private static void setupMenu(Menu addTo){
        Menu menuNewGraph = new Menu("New Graph");
            
        MenuItem menuNewGraphEmpty = new MenuItem("Empty Graph");
                 menuNewGraphEmpty.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
                 menuNewGraphEmpty.setOnAction((ActionEvent t) -> {
                     IntegratedGraphing.getHQ().addGraph(new Graph());
                 });

        MenuItem menuNewGraphK = new MenuItem("K_n Graph");
                 menuNewGraphK.setAccelerator(KeyCombination.keyCombination("Ctrl+k"));
                 menuNewGraphK.setOnAction((ActionEvent t) -> {
                    IntegratedGraphing.getHQ().addKGraph();
                 });
                 
        MenuItem menuExitButton = new MenuItem("Exit");
        menuExitButton.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuExitButton.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });

        menuNewGraph.getItems().addAll(menuNewGraphEmpty,menuNewGraphK);
        addTo.getItems().addAll(menuNewGraph, menuExitButton);
    }

    private static void setupGraphs(Menu addTo){
        MenuItem complement = new MenuItem("Complement Graph");
        complement.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        complement.setOnAction((ActionEvent t) -> {
            IntegratedGraphing.getHQ().complementGraph();
        });

        MenuItem isomorphic = new MenuItem("Check for isomorphism.");
        isomorphic.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        isomorphic.setOnAction((ActionEvent t) -> {
            IntegratedGraphing.getHQ().checkIsomorphism();
        });
        
        MenuItem union = new MenuItem("Union Graphs");
        union.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
        union.setOnAction((ActionEvent t) -> {
            IntegratedGraphing.getHQ().unionGraphs();
        });

        addTo.getItems().addAll(complement, isomorphic, union);
    }

    private static void showNeedTwoGraphsAlert(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Item Error");
        alert.setContentText("You must be working with at least two graphs.");
        alert.show();
    }
}
