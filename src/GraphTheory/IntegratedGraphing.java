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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Thayer
 */
public class IntegratedGraphing extends Application {
    BorderPane root;
    Canvas canvas;
    TitledPane graphsPane;
    ScrollPane graphsContent;
    TitledPane detailsPane;
    ScrollPane detailsContent;
    
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
        StackPane renderings = new StackPane();
        renderings.prefHeight(GuiConstants.RENDERINGS_HEIGHT);
        renderings.prefWidth(GuiConstants.RENDERINGS_WIDTH);
        root.setCenter(renderings);
        
        Pane toolPalette = new Pane();
        toolPalette.setMinSize(GuiConstants.TOOL_ICON_SIZE, Tool.values().length * GuiConstants.TOOL_ICON_SIZE);
        toolPalette.setPrefWidth(GuiConstants.TOOL_ICON_SIZE + 2 * GuiConstants.TOOL_MARGIN_SIZE);
        toolPalette.setMaxHeight(Tool.values().length * GuiConstants.TOOL_ICON_SIZE 
                + Tool.values().length * GuiConstants.TOOL_MARGIN_SIZE);
        toolPalette.setStyle("-fx-background-color: white;");
        root.setLeft(toolPalette);
        
        Scene mainScene = new Scene(root,GuiConstants.SCENE_WIDTH,GuiConstants.SCENE_HEIGHT);
        
        createMenus(mainScene,root);
        
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
    
    private void createMenus(Scene s, BorderPane root){
        MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuGraphs = new Menu("Graphs");
        Menu menuOptions = new Menu("Options");
        mainMenu.getMenus().addAll(menuFile,menuGraphs,menuOptions);
        
        detailsContent = new ScrollPane();
        detailsPane = new TitledPane();
        detailsPane.setText("Details");
        detailsPane.setCollapsible(true);
        detailsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        detailsPane.setContent(detailsContent);
        
        graphsContent = new ScrollPane();
        graphsContent.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        graphsPane = new TitledPane();
        graphsPane.setText("Graphs");
        graphsPane.setCollapsible(true);
        graphsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        graphsPane.setContent(graphsContent);
        
        GridPane grid = new GridPane();
        
        graphsContent.prefHeightProperty().bind(grid.heightProperty());
        detailsContent.prefHeightProperty().bind(grid.heightProperty());
        grid.add(graphsPane, 0, 0);
        grid.add(detailsPane, 0, 2);
        
        mainMenu.prefWidthProperty().bind(s.widthProperty());
        root.setTop(mainMenu);
        root.setRight(grid);
    }
}
