/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory;

import GraphTheory.Graphs.Graph;
import GraphTheory.Mouse.MouseGestures;
import GraphTheory.Mouse.ToolManager;
import GraphTheory.Utility.Logger;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    TitledPane graphsPane;
    BorderPane graphsContentOrganizer;
    ScrollPane graphsContentScroll;
    AnchorPane graphsContent;
    TitledPane detailsPane;
    BorderPane detailsContentOrganizer;
    ScrollPane detailsContentScroll;
    AnchorPane detailsContent;
    VBox sidebar;
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
        mainDirectory = new String(findDirectory("img") + File.separator);
        imageDirectory = new String(mainDirectory + "img" + File.separator);
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
        myGraph.reorderNodes(100);
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
        StackPane renderings = new StackPane();
        renderings.prefHeight(GuiConstants.RENDERINGS_HEIGHT);
        renderings.prefWidth(GuiConstants.RENDERINGS_WIDTH);
        renderings.setStyle("-fx-background-color: white;");
        root.setCenter(renderings);
        
        Logger.log("Creating the menus:",1);
        createMenus(s,root);
        
        Logger.log("Creating the sidebar:",1);
        setupGraphsPane();
        
        Logger.log("Aligning the components.",1);
        GridPane grid = new GridPane();        
        graphsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        detailsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        grid.add(graphsPane, 0, 0);
        grid.add(detailsPane, 0, 2);
        root.setRight(grid);
        
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
    
    private void setupGraphsPane(){
        detailsPane = new TitledPane();
        detailsPane.setText("Details");
        detailsPane.setCollapsible(true);
        detailsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        
        detailsContentScroll = new ScrollPane();
        detailsContentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        detailsContent = new AnchorPane();
        detailsContent.setPrefWidth(detailsContentScroll.getWidth());
        detailsContentScroll.setContent(detailsContent);
        
        detailsContentOrganizer = new BorderPane();
        detailsContentOrganizer.setCenter(detailsContentScroll);
        
        detailsPane.setContent(detailsContentOrganizer);
        
        graphsContent = new AnchorPane();
        graphsContent.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        
        graphsContentScroll = new ScrollPane();
        graphsContentScroll.setContent(graphsContent);
        graphsContentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        graphsContentOrganizer = new BorderPane();
        graphsContentOrganizer.setCenter(graphsContentScroll);        
        
        graphsPane = new TitledPane();
        graphsPane.setText("Graphs");
        graphsPane.setCollapsible(true);
        graphsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        graphsPane.setContent(graphsContentOrganizer);
        
        Logger.log("Graph pane setup complete.", 2);
    }
    
    public class DetailsRow{
        HBox row = new HBox();
        TextField leftField;
        TextField rightField;
        Tooltip tooltip;
        
        public DetailsRow(String left, String right, Tooltip tipIn){
            leftField = new TextField(left);
            rightField = new TextField(right);
            leftField.setTooltip(tipIn);
            rightField.setTooltip(tipIn);
            leftField.setPrefWidth(GuiConstants.DETAILS_CELL_WIDTH);
            rightField.setPrefWidth(GuiConstants.DETAILS_CELL_WIDTH);
            row.setMaxHeight(GuiConstants.DETAILS_ROW_HEIGHT);
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
}
