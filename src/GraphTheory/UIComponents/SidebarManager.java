/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.GuiConstants;
import GraphTheory.Utility.Logger;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Thayer
 */
public class SidebarManager {
    private static TitledPane graphsPane;
    private static BorderPane graphsContentOrganizer;
    private static ScrollPane graphsContentScroll;
    private static TreeView graphsContent;
    private static TreeItem<String> graphRoot;
    private static TitledPane detailsPane;
    private static BorderPane detailsContentOrganizer;
    private static ScrollPane detailsContentScroll;
    private static VBox detailsContent;
    private static GridPane grid;
    private static DetailsSet graphDetails;

    public static GridPane setupSidebar(){
        Logger.log("Creating the details pane.", 2);
        detailsPane = new TitledPane();
        detailsPane.setText("Details");
        detailsPane.setCollapsible(true);
        detailsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        detailsPane.setOnMouseClicked(expandedChecker);
        
        detailsContentScroll = new ScrollPane();
        detailsContentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        detailsContent = new VBox();
        detailsContent.setPrefWidth(detailsContentScroll.getWidth());
        detailsContentScroll.setContent(detailsContent);
        
        detailsContentOrganizer = new BorderPane();
        detailsContentOrganizer.setCenter(detailsContentScroll);
        
        detailsPane.setContent(detailsContentOrganizer);
        
        Logger.log("Setting up the graphs pane.", 2);
        graphRoot = new TreeItem<>("root");
        graphRoot.setExpanded(true);
        graphsContent = new TreeView(graphRoot);
        graphsContent.setShowRoot(false);
        graphsContent.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        graphsContent.getSelectionModel()
            .selectedItemProperty()
            .addListener((new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observable,
                                    TreeItem<String> old_val, TreeItem<String> new_val) {
                    GraphManager.setCurrentGraph(new_val.getValue());
                }
            }));
        
        graphsContentScroll = new ScrollPane();
        graphsContentScroll.setContent(graphsContent);
        graphsContentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        graphsContent.prefHeightProperty().bind(graphsContentScroll.heightProperty());
        
        graphsContentOrganizer = new BorderPane();
        graphsContentOrganizer.setCenter(graphsContentScroll);        
        
        graphsPane = new TitledPane();
        graphsPane.setText("Graphs");
        graphsPane.setCollapsible(true);
        graphsPane.setPrefWidth(GuiConstants.SIDEBAR_WIDTH);
        graphsPane.setContent(graphsContentOrganizer);
        graphsPane.setOnMouseClicked(expandedChecker);
        
        Logger.log("Organizing the components.", 2);
        grid = new GridPane();        
        graphsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        detailsContentOrganizer.prefHeightProperty().bind(grid.heightProperty());
        grid.add(graphsPane, 0, 0);
        grid.add(detailsPane, 0, 2);
        
        Logger.log("Graph pane setup complete.", 2);
        return grid;
    }

    public static void addDetails(){
        graphDetails = new DetailsSet(GraphManager.get(GraphManager.size() - 1).represents);
        detailsContent.getChildren().addAll(graphDetails.getRows());
    }
    
    public static void setDetails(GraphEntity e){
        if(graphDetails!=null) //prevents an error in initiation, better solution later maybe
            graphDetails.changeTarget(e.represents);
    }
    
    public static void addGraph(GraphEntity e){        
        graphRoot.getChildren().add(e.tag);
        graphsContent.getSelectionModel().select(e.tag);
    }
    
    public static void setSelected(GraphEntity e){
        graphsContent.getSelectionModel().select(e.tag);
    }
    
    private static final EventHandler<MouseEvent> expandedChecker = (MouseEvent event) -> {
        if(graphsPane.expandedProperty().get() == false
                && detailsPane.expandedProperty().get() == false){
            graphsPane.setExpanded(true);
            detailsPane.setExpanded(true);
        }
    };
}
