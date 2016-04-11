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
import GraphTheory.Utility.OptionsManager;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

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
        Menu menuView = new Menu("View");
        Menu menuOptions = new Menu("Options");

        mainMenu.getMenus().addAll(menuFile,menuGraphs,menuView,menuOptions);
        mainMenu.prefWidthProperty().bind(s.widthProperty());
        
        setupMenu(menuFile);
        setupGraphs(menuGraphs);
        setupView(menuView);

        Logger.log("Setting up the toolbar.",2);
        toolbar = new ToolBar();
        toolbar.getItems().add(ToolManager.setupToolManager());

        VBox menuContainer = new VBox(mainMenu,toolbar);
        Logger.log("Setting up menus complete.", 2);
        return menuContainer;
    }

    private static void setupMenu(Menu addTo){
        MenuItem menuNewWorkspace = new MenuItem("New Workspace");
                 menuNewWorkspace.setOnAction((ActionEvent t) -> {
                     IntegratedGraphing.getHQ().newWorkspace();
                 });
        
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

        MenuItem menuSaveButton = new MenuItem("Save");
                 menuSaveButton.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
                 menuSaveButton.setOnAction((ActionEvent t) -> {
                     IntegratedGraphing.getHQ().saveAsToFile();
                 });
                 
        MenuItem menuExitButton = new MenuItem("Exit");
        menuExitButton.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuExitButton.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });

        menuNewGraph.getItems().addAll(menuNewGraphEmpty,menuNewGraphK);
        addTo.getItems().addAll(menuNewWorkspace, menuNewGraph, menuSaveButton,
                                menuExitButton);
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
        
        MenuItem selfIsomorphic = new MenuItem("Check for auto-isomorphism.");
        selfIsomorphic.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        selfIsomorphic.setOnAction((ActionEvent t) -> {
            IntegratedGraphing.getHQ().checkSelfIsomorphism();
        });
        
        MenuItem union = new MenuItem("Union Graphs");
        union.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
        union.setOnAction((ActionEvent t) -> {
            IntegratedGraphing.getHQ().unionGraphs();
        });

        addTo.getItems().addAll(complement, isomorphic, selfIsomorphic, union);
    }

    private static void setupView(Menu addTo){
        CheckMenuItem vertexLabelToggle = new CheckMenuItem("View Vertex Labels");
        vertexLabelToggle.setSelected(IntegratedGraphing.getHQ().getOptionsManager().getShowVertexLabels());
        vertexLabelToggle.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        vertexLabelToggle.setOnAction((ActionEvent t) -> {
            boolean shouldShow = vertexLabelToggle.isSelected();
            IntegratedGraphing.getHQ().setShowVertexLabels(shouldShow);
        });
        addTo.getItems().addAll(vertexLabelToggle);
    }

    private static void showNeedTwoGraphsAlert(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Item Error");
        alert.setContentText("You must be working with at least two graphs.");
        alert.show();
    }
}
