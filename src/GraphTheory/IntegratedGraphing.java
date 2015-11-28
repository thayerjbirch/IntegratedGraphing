/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory;

import GraphTheory.Graphs.Graph;
import GraphTheory.Mouse.MouseGestures;
import GraphTheory.Mouse.Utility;
import GraphTheory.Mouse.Utility.Tool;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Thayer
 */
public class IntegratedGraphing extends Application {
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
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        //testSetup(primaryStage);
        setupRoutine(primaryStage);
    }
    
    private void setupRoutine(Stage primaryStage){
        primaryStage.setTitle("Rendered Graph Theory");
        root = new BorderPane();
        Scene mainScene = new Scene(root,GuiConstants.SCENE_WIDTH,GuiConstants.SCENE_HEIGHT);
        
        createLayout(mainScene,root);
        
        primaryStage.setScene(mainScene);
        primaryStage.show();
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
        myGraph.compliment();
        System.out.println(myGraph.getEdgeSet().size());
        root.getChildren().add(myGraph.graphContents);
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    private void createLayout(Scene s, BorderPane root){
        StackPane renderings = new StackPane();
        renderings.prefHeight(GuiConstants.RENDERINGS_HEIGHT);
        renderings.prefWidth(GuiConstants.RENDERINGS_WIDTH);
        renderings.setStyle("-fx-background-color: white;");
        root.setCenter(renderings);
        
        createMenus(s,root);
        setupGraphsPane();
        
        GridPane grid = new GridPane();        
        graphsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        detailsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        grid.add(graphsPane, 0, 0);
        grid.add(detailsPane, 0, 2);
        root.setRight(grid);
        
    }
    
    private void createMenus(Scene s, BorderPane root){
        MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuGraphs = new Menu("Graphs");
        Menu menuOptions = new Menu("Options");
        mainMenu.getMenus().addAll(menuFile,menuGraphs,menuOptions);    
        mainMenu.prefWidthProperty().bind(s.widthProperty()); 
        
        ToolBar toolBar = new ToolBar();
        
        VBox menuContainer = new VBox(mainMenu,toolBar);
        root.setTop(menuContainer);
    }
    
    private void setupGraphsPane(){
        detailsPane = new TitledPane();
        detailsPane.setText("Details");
        detailsPane.setCollapsible(true);
        detailsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        
        detailsContent = new AnchorPane();
        detailsContent.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        detailsContent.setMinWidth(GuiConstants.SIDEBAR_WIDTH);
        
        detailsContentScroll = new ScrollPane();
        detailsContentScroll.setContent(detailsContent);
        detailsContentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
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
    }
}
