/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory;

import GraphTheory.Graphs.Graph;
import GraphTheory.Input.ToolManager;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.UIComponents.MenuManager;
import GraphTheory.UIComponents.RenderingsManager;
import GraphTheory.UIComponents.SidebarManager;
import GraphTheory.Utility.FileManager;
import GraphTheory.Utility.Headquarters;
import GraphTheory.Utility.Logger;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Thayer
 */
public class IntegratedGraphing extends Application {
    public static String imageDirectory = null;
    public static String mainDirectory = null;
    public static String dataDirectory = null;
    private static Stage primaryStage;
    private static Headquarters hq;
    BorderPane root;
    Canvas canvas;
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws IOException {
        testSetup(primaryStage);
//        setupRoutine(primaryStage);
//        fileManager.loadFromFile();
    }
    
    private void setupRoutine(Stage primaryStage){
        IntegratedGraphing.primaryStage = primaryStage;

        mainDirectory = findDirectory("img") + File.separator;
        imageDirectory = mainDirectory + "img" + File.separator;
        dataDirectory = mainDirectory + "dat" + File.separator;
        FileManager.ensureExists(dataDirectory);

        Logger.initialize(new File(mainDirectory), true);
        Logger.log("Application Started.");
        Logger.log("The application directory:" + mainDirectory);
        Logger.log("Image resource directory:" + mainDirectory);
        
        primaryStage.setTitle("Rendered Graph Theory");
        root = new BorderPane();
        Scene mainScene = new Scene(root,GuiConstants.SCENE_WIDTH,GuiConstants.SCENE_HEIGHT);
        
        hq = new Headquarters();
        
        Logger.log("Setting up the layout:");
        createLayout(mainScene, root, hq.getRenderingsMgr(), hq.getSidebarMgr());

        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            Logger.log("Exit request recieved, autosaving...");
            System.out.println(Boolean.toString(hq.getOptionsManager().getShowEdgeLabels()));
            hq.saveOptions();
            hq.saveToFile();
            Logger.log("Success, application will now exit.");
        });        
        primaryStage.setScene(mainScene);
        primaryStage.show();
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
        setupRoutine(primaryStage);
//        hq.loadFromFile();
        hq.addGraph("K6", Graph.buildKGraph(6));
        hq.addGraph("K4", Graph.buildKGraph(4));
    }
    
    private void createLayout(Scene s, BorderPane root, RenderingsManager renderingsManager,
                    SidebarManager sidebarManager){
        Logger.log("Creating the main content pane.",1);
        root.setCenter(renderingsManager.getPane());
        
        Logger.log("Creating the menus:",1);
        root.setTop(MenuManager.createMenus(s));
        
        Logger.log("Creating the sidebar:",1);        
        root.setRight(sidebarManager.getGrid());
        
        Logger.log("Layout setup complete.",1);
    }

    public static Headquarters getHQ(){
        return hq;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}
