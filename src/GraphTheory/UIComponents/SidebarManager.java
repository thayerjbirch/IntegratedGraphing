/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.UIComponents;

import GraphTheory.GuiConstants;
import GraphTheory.IntegratedGraphing;
import GraphTheory.Utility.Logger;
import javafx.beans.value.ChangeListener;
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
    private TitledPane graphsPane;
    private final BorderPane graphsContentOrganizer;
    private final ScrollPane graphsContentScroll;
    private final TreeView graphsContent;
    private final TreeItem<String> graphRoot;
    private TitledPane detailsPane;
    private final BorderPane detailsContentOrganizer;
    private final ScrollPane detailsContentScroll;
    private final VBox detailsContent;
    private final GridPane grid;
    private DetailsSet graphDetails;

    public SidebarManager(){
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
                    try{
                        IntegratedGraphing.getHQ().setCurrentGraph(new_val.getValue());
                    }
                    catch(Exception ex){
                        Logger.log("Attempt to change selected graph failed.");
                        Logger.log(ex.toString());
                    }
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
    }

    public GridPane getGrid(){
        return grid;
    }

    public void setCurrentGraph(GraphEntity e){
        setSelected(e);
        setDetails(e);
    }
    
    public void addDetails(){
//        graphDetails = new DetailsSet(IntegratedGraphing.getGraphManager().get(IntegratedGraphing.getGraphManager().size() - 1).represents);
        System.out.println("Go look at SidebarManager.addDetails()");
        detailsContent.getChildren().addAll(graphDetails.getRows());
    }
    
    public void setDetails(GraphEntity e){
        if(graphDetails!=null) //prevents an error in initiation, better solution later maybe
            graphDetails.changeTarget(e.represents);
    }
    
    public void addGraph(GraphEntity e){        
        graphRoot.getChildren().add(e.tag);
        graphsContent.getSelectionModel().select(e.tag);
    }

    public void removeGraph(GraphEntity e){
        graphRoot.getChildren().remove(e.tag);
        if(graphsContent.getSelectionModel().getSelectedItem().equals(e))
            try{
                graphsContent.getSelectionModel().select(0);
            }
            catch(IndexOutOfBoundsException ex){
                Logger.log("Tried to autoselect non-existant graph during deletion.");
                ex.printStackTrace();
            }
    }
    
    public void setSelected(GraphEntity e){
        graphsContent.getSelectionModel().select(e.tag);
    }
    
    private final EventHandler<MouseEvent> expandedChecker = (MouseEvent event) -> {
        if(graphsPane.expandedProperty().get() == false
                && detailsPane.expandedProperty().get() == false){
            graphsPane.setExpanded(true);
            detailsPane.setExpanded(true);
        }
    };
}
