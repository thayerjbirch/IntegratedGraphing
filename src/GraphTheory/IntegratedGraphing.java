/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory;

import GraphTheory.Graphs.Graph;
import GraphTheory.Mouse.MouseGestures;
import GraphTheory.Mouse.ToolManager;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.UIComponents.RenderingsManager;
import GraphTheory.UIComponents.SidebarManager;
import GraphTheory.Utility.Logger;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Thayer
 */
public class IntegratedGraphing extends Application {
    public static String imageDirectory = null;
    public static String mainDirectory = null;
    BorderPane root;
    Canvas canvas;
    ToolBar toolbar;
    ToolManager toolManager;
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws IOException {
        //testSetup(primaryStage);
        setupRoutine(primaryStage);
    }
    
    private void setupRoutine(Stage primaryStage){
        mainDirectory = findDirectory("img") + File.separator;
        imageDirectory = mainDirectory + "img" + File.separator;
        Logger.initialize(new File(mainDirectory), true);
        Logger.log("Application Started.");
        Logger.log("The application directory:" + mainDirectory);
        Logger.log("Image resource directory:" + mainDirectory);
        
        primaryStage.setTitle("Rendered Graph Theory");
        root = new BorderPane();
        Scene mainScene = new Scene(root,GuiConstants.SCENE_WIDTH,GuiConstants.SCENE_HEIGHT);
        
        Logger.log("Setting up the layout:");
        createLayout(mainScene,root);
        
        primaryStage.setScene(mainScene);
        primaryStage.show();
        
        GraphManager.addGraph("K6",Graph.buildKGraph(6));
        GraphManager.addGraph("K4",Graph.buildKGraph(4));
    }
    
    public String findDirectory(String targetDir){
        System.out.println("Finding application path:");
        String applicationDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        applicationDir = applicationDir.replaceAll("%20", " ");
        applicationDir = new File(applicationDir).getParent();
        System.out.println("\t1: checking " + applicationDir);
        if(new File(applicationDir + File.separator + targetDir).exists())
            return applicationDir;
        //checks parent directory if avr not found, common if you run outside of jar
        applicationDir = new File(applicationDir).getParent();
        System.out.println("\t2: checking " + applicationDir);
        if(new File(applicationDir + File.separator + targetDir).exists())
            return applicationDir;
        
        applicationDir = new File(applicationDir).getParent();
        System.out.println("\t3: checking " + applicationDir);
        if(new File(applicationDir + File.separator + targetDir).exists())
            return applicationDir;
        System.out.println("Defaulting to user.dir, checking:");
        if(new File(System.getProperty("user.dir") + File.separator + targetDir).exists())
            return System.getProperty("user.dir");
        return null;
    }
    
    private void testSetup(Stage primaryStage){
        primaryStage.setTitle("Drawing Operations Test");
        root = new BorderPane();
        canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        
        Graph myGraph = Graph.buildKGraph(canvas.getWidth() / 2, canvas.getHeight() / 2, 8);
        myGraph.reorderVertexs(100);
        MouseGestures.addGestures(myGraph);
        System.out.println(myGraph.getEdgeSet().size());
        //myGraph.compliment();
        System.out.println(myGraph.getEdgeSet().size());
        root.getChildren().add(myGraph.graphContents);
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    private void createLayout(Scene s, BorderPane root){
        Logger.log("Creating the main content pane.",1);
        root.setCenter(RenderingsManager.setupRenderings());
        
        Logger.log("Creating the menus:",1);
        createMenus(s,root);
        
        Logger.log("Creating the sidebar:",1);        
        root.setRight(SidebarManager.setupSidebar());
        
        Logger.log("Layout setup complete.",1);
    }
    
    private void createMenus(Scene s, BorderPane root){
        Logger.log("Setting up the main menu bar.",2);
        MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuGraphs = new Menu("Graphs");
        Menu menuOptions = new Menu("Options");
        mainMenu.getMenus().addAll(menuFile,menuGraphs,menuOptions);    
        mainMenu.prefWidthProperty().bind(s.widthProperty()); 
        
        Logger.log("Setting up the toolbar.",2);
        toolbar = new ToolBar();
        toolbar.getItems().add(ToolManager.setupToolManager());
        
        VBox menuContainer = new VBox(mainMenu,toolbar);
        root.setTop(menuContainer);
        Logger.log("Setting up menus complete.", 2);
    }
}
