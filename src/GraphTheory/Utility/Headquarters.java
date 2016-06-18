/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import GraphTheory.Graphs.Graph;
import GraphTheory.Graphs.GraphEdge;
import GraphTheory.Graphs.GraphObject.GraphCircle;
import GraphTheory.Graphs.GraphVertex;
import GraphTheory.Graphs.Translatable;
import GraphTheory.Input.ToolManager;
import GraphTheory.IntegratedGraphing;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.UIComponents.MultiSelectionDialog;
import GraphTheory.UIComponents.RenderingsManager;
import GraphTheory.UIComponents.SidebarManager;
import java.io.File;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;

/**
 *
 * @author Thayer
 */
public class Headquarters {
    RenderingsManager renderingsManager;
    GraphManager graphManager;
    SidebarManager sidebarManager;
    FileManager fileManager;
    OptionsManager optionsManager;
    ToolManager toolManager;
    FileChooser fc;
    
    /**
     * Default constructor
     */
    public Headquarters(){
        graphManager = new GraphManager();
        renderingsManager = new RenderingsManager();
        Graph.setRenderer(renderingsManager);
        sidebarManager = new SidebarManager();
        fileManager = new FileManager(IntegratedGraphing.dataDirectory);
        optionsManager = fileManager.loadOptions();

        if(optionsManager == null){//first time running or file was deleted
            optionsManager = new OptionsManager(this);
        }

        fc = new FileChooser();
        fc.setInitialDirectory(new File(IntegratedGraphing.dataDirectory));
        fc.getExtensionFilters().addAll(
            new ExtensionFilter("Graph Workspace Files", "*.grph"),
            new ExtensionFilter("All Files", "*.*"));
    }

    /**
     * Sets the parameter to the current graph entity
     * @param e
     */
    public void setCurrentGraph(GraphEntity e){
        if(!e.equals(getCurrentGraph())){
            graphManager.setCurrentGraph(e);
            sidebarManager.setCurrentGraph(e);
        }
    }

    /**
     * Sets the given index as the currently selected graph entity
     * @param index
     */
    public void setCurrentGraph(int index){
        setCurrentGraph(graphManager.get(index));
    }
    
    /**
     * Sets the current graph entity using the parameter as a
     * key value 
     * @param tag
     */
    public void setCurrentGraph(String tag){
        GraphEntity e = graphManager.get(tag);
        if(e!=null)
            setCurrentGraph(e);
    }
    
    /**
     * Sets the current graph entity using the parameter graph
     * @param g
     */
    public void setCurrentGraph(Graph g){
        GraphEntity temp = null;
        for(GraphEntity e : graphManager.getGraphs())
            if(e.getGraph() == g){
                temp = e;
                break;
            }
        if(temp!=null){
            setCurrentGraph(temp);
        }
        else
            Logger.log("Could not find an entity containing the specified graph.");
    }

    /**
     * Helper method, wraps the Graph passed in with a GraphEntity
     * @param g
     */
    public void addGraph(Graph g){
        addGraph(new GraphEntity(g));
    }
     
    /**
     * Helper method, wraps the Graph passed in with a GraphEntity and
     * sets the name of the graph to the string passed in.
     * @param name
     * @param g
     */
    public void addGraph(String name, Graph g){
        addGraph(new GraphEntity(name,g));        
    }

    /**
     * Adds the passed in GraphEntity to the GraphManager and sets
     * the ui elements to reflect the new graph.
     * @param e
     */
    public void addGraph(GraphEntity e){
        graphManager.addGraph(e);
        sidebarManager.addGraph(e);
    }

    /**
     * Prompts a user for a number and then creates a new complete graph
     * of the chosen size
     */
    public void addKGraph(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Graph");
        dialog.setHeaderText("Enter the desired order for your K-graph.");

        Optional<String> stringIn = dialog.showAndWait();
        stringIn.ifPresent((String in) -> {
            try{
                int n = Integer.parseInt(in);
                addGraph('K' + Integer.toString(n), Graph.buildKGraph(n));
            } catch(Exception e){
                Alert a = new Alert(AlertType.ERROR, "Must input an integer without extraneous characters.");
                a.setHeaderText("Invalid Input");
                Logger.log(e.toString());
            }
        });
    }

    /**
     * Removes the given graph entity from the GraphManager and any of its
     * associated nodes from the UI
     * @param e
     */
    public void removeGraph(GraphEntity e){
        Logger.log("Removing graph: " + e.getName());
        renderingsManager.removeNode(e.getGraph().getCircle());
        renderingsManager.removeAll(e.getGraph().getContents());
        sidebarManager.removeGraph(e);
        graphManager.removeGraph(e);
    }

    /**
     * Attempts to draw a new edge between two vertices
     * @param event
     */
    public void edgeAddToolAction(MouseEvent event){
        GraphVertex srcVertex =  (GraphVertex) ((GraphCircle)event.getSource()).getRepresents();
        setCurrentGraph(srcVertex.getParent());

        if(renderingsManager.getSelected() == null){
            renderingsManager.setSelected(srcVertex);
            Logger.log("Set the starting vertex.");
        }
        else{
            if(srcVertex.equals(renderingsManager.getSelected()))
                return;
            if(graphManager.addEdge(renderingsManager.getSelected(),srcVertex)){
                //If the adding the edge succeeds. It will fail if the two vertices are
                //in distinct Graphs
                renderingsManager.setSelected(null);
            }
        }
    }

    /**
     * This function sets the current graph to whatever is selected
     * by the pointer.
     * @param event
     * @param t
     */
    public void pointerPressed(MouseEvent event, Translatable t){
        renderingsManager.removeGlow();
        if(t instanceof Graph)
            setCurrentGraph((Graph)t);
        else if(t instanceof GraphVertex)
            setCurrentGraph(((GraphVertex)t).getParent());
        else if(t instanceof GraphEdge)
            setCurrentGraph(((GraphEdge)t).getParent());
    }

    /**
     * Ensures the name of every graph is unique by appending
     * numbers to duplicate names
     * @param nameIn
     * @return
     */
    public String checkName(String nameIn){
        int i = 0;
        String name = nameIn;
        if(graphManager.hasGraph(name)){
            String suffix = " (1)";
            while(graphManager.hasGraph(name + suffix)){
                i+=1;
                suffix = " (" + Integer.toString(i) + ")";
            }
            name = nameIn + suffix;
        }
        return name;
    }

    /**
     * Routing method to remove an edge from the Graph Manager
     * @param edge
     */
    public void edgeDeleted(GraphEdge edge){
        graphManager.removeEdge(edge);
    }

    /**
     * Routing method to remove an vertex from the Graph Manager
     * @param vertex
     */
    public void vertexDeleted(GraphVertex vertex){
        graphManager.removeVertex(vertex);
    }

    /**
     * Routing method to add a vertex to the Graph manager at (x,y)
     * @param x
     * @param y
     */
    public void vertexAdded(double x, double y){
        graphManager.addVertex(x,y);
    }

    /**
     * Removes all edges from the selected graph, and adds any graphs
     * that were not previously in that graph.
     */
    public void complementGraph(){
        ChoiceDialog<String> selectionDialog = new ChoiceDialog<>(
             graphManager.curGraphEntity.getName(), graphManager.getGraphNames());
        selectionDialog.setTitle("Complement");
        selectionDialog.setHeaderText("Select which graph to complement:");

        Optional<String> result = selectionDialog.showAndWait();
        result.ifPresent((String target) -> {
            Graph g = graphManager.get(target).getGraph();
            g.complement();
            g.verticesToFront();
        });
    }

    /**
     * Determines if the two chosen graphs are isomorphic
     */
    public void checkIsomorphism(){
        Logger.log("Begining isomorphism checking routine.");
        if(graphManager.getGraphs().size() < 2){
            Logger.log("Aborted attemt to check for isomorphisms, too few graphs in workspace.",1);

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Item Error");
            alert.setContentText("You must be working with at least two graphs"
                                +" to compare for isomorphism");
            alert.show();
            return;
        }
        Optional<Pair<String,String>> choices = new MultiSelectionDialog(graphManager.getGraphNames(),
                                        "Select two graphs to check for an isomorphism.").showAndWait();

        choices.ifPresent(choicePair -> {
            String result;
            GraphEntity g1 = graphManager.get(choicePair.getKey());
            GraphEntity g2 = graphManager.get(choicePair.getValue());

            boolean isIso = Graph.isomorphic(g1, g2);
            Alert alert = new Alert(AlertType.INFORMATION);

            if(isIso){
                result = g1.getName() + " is isomorphic to " + g2.getName();
                alert.setHeaderText("Sucess");
            }
            else{
                result = g1.getName() + " is not isomorphic to " + g2.getName();
                alert.setHeaderText("Failure");
            }
            Logger.log(result,1);
            alert.setTitle("Isomorphism Results");
            alert.setContentText(result);
            alert.show();
        });
        Logger.log("Isomorphism checker finished.");
    }

    /**
     * Determines if the selected graph has an auto isomorphism
     */
    public void checkSelfIsomorphism(){
        ChoiceDialog<String> selectionDialog = new ChoiceDialog<>(
             graphManager.curGraphEntity.getName(), graphManager.getGraphNames());
        selectionDialog.setTitle("Complement");
        selectionDialog.setHeaderText("Select which graph to complement:");

        Optional<String> diaResult = selectionDialog.showAndWait();
        diaResult.ifPresent((String target) -> {
            GraphEntity g1 = graphManager.get(target);
            GraphEntity g2 = g1.getCopy(false);
            String result;

            g2.getGraph().complement();
         
            boolean isIso = Graph.isomorphic(g1, g2);
            Alert alert = new Alert(AlertType.INFORMATION);

            if(isIso){
                result = g1.getName() + " is isomorphic to " + g2.getName();
                alert.setHeaderText("Sucess");
            }
            else{
                result = g1.getName() + " is not isomorphic to " + g2.getName();
                alert.setHeaderText("Failure");
            }

            Logger.log(result,1);
            alert.setTitle("Isomorphism Results");
            alert.setContentText(result);
            alert.show();
        });
    }

    /**
     * @return The currently selected graph
     */
    public GraphEntity getCurrentGraph(){
        return graphManager.getCurrentGraph();
    }

    /**
     * @return the OptionsManager
     */
    public OptionsManager getOptionsManager(){
        return optionsManager;
    }

    /**
     * Combines the selected graphs into one graph with the vertices
     * and edges of both
     */
    public void unionGraphs(){
        Logger.log("Begining union routine.");
        if(graphManager.getGraphs().size() < 2){
            Logger.log("Aborted attemt to check for union, too few graphs in workspace.",1);


            return;
        }
        Optional<Pair<String,String>> choices = new MultiSelectionDialog(graphManager.getGraphNames(),
                                        "Select two graphs to check for an isomorphism.").showAndWait();

        choices.ifPresent(choicePair -> {
            GraphEntity g1 = graphManager.get(choicePair.getKey());
            GraphEntity g2 = graphManager.get(choicePair.getValue());
            Logger.log("Two graphs chosen for merging.");
            graphManager.unionGraphs(g1, g2);
        });
    }

    /**
     * Writes the current options and graph elements to a file
     */
    public void saveToFile(){
        fileManager.saveToFile(graphManager.getGraphs());
    }

    /**
     * Writes the current options and graph elements to a new file
     */
    public void saveAsToFile(){
        fc.setTitle("Save file as:");
        File newSave = fc.showSaveDialog(IntegratedGraphing.getPrimaryStage());

        fileManager.saveToFile(newSave, graphManager.getGraphs());
    }

    /**
     * Removes all elements and creates a new workspace
     */
    public void clear(){
        Logger.log("Clearing workspace contents.");
//        Logger.log(Integer.toString(graphManager.getGraphs().size()));
//        Iterator<GraphEntity> iter = graphManager.getGraphs().iterator();
////        while(iter.hasNext()){ //enhanced for loop was throwing concurrent modification errors
//        for(int i = 0; i < graphManager.getGraphs().size(); i++){
////            removeGraph(iter.next());
////            System.out.println(iter.hasNext());
//            System.out.println(i);
//            removeGraph(graphManager.getGraphs().get(graphManager.getGraphs().size() - 1));
//            System.out.println(graphManager.getGraphs().size());
//        }
        renderingsManager.clear();
        graphManager.clear();
        sidebarManager.clear();
        Logger.log("Finished clearing workspace contents.");
    }

    /**
     * Loads the settings from a previous session
     */
    public void loadFromFile(){
        Logger.log("Attempting to load from file.");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your saved file:");
        fc.setInitialDirectory(new File(IntegratedGraphing.dataDirectory));
        fc.getExtensionFilters().addAll(
            new ExtensionFilter("Graph Workspace Files", "*.grph"),
            new ExtensionFilter("All Files", "*.*"));
        File target = fc.showOpenDialog(IntegratedGraphing.getPrimaryStage());

        if(target!=null){
            clear();
            fileManager.loadFromFile(target.toString());
        }
    }

    public void tryAutoLoad(){
        fileManager.loadFromFile();
    }

    /**
     * Shows confirmation dialog before clearing workspace
     */
    public void newWorkspace(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("New Workspace");
        alert.setHeaderText("Clear work and create a new workspace.");
        alert.setContentText("Are you sure you want to clear your work? Unsaved" + 
                             " changes will be discarded.");
        Optional<ButtonType> response = alert.showAndWait();

        response.ifPresent((ButtonType bt) -> {
            if(bt == ButtonType.OK)
                clear();
        });
    }

    /**
     * Writes options to a file
     */
    public void saveOptions(){
        fileManager.saveOptions(optionsManager);
    }

    public void setShowVertexLabels(boolean b){
        optionsManager.setShowVertexLabels(b);

        if(b){
            for(GraphEntity e : graphManager.getGraphs())
                for(Node n : e.getVertexLabels()){
                    renderingsManager.addNode(n);
                }
        }
        else{
            for(GraphEntity e : graphManager.getGraphs())
                for(Node n : e.getVertexLabels()){
                    renderingsManager.removeNode(n);
                }
        }
    }

    public void setShowEdgeLabels(boolean b){
        optionsManager.setShowEdgeLabels(b);

        if(b){
            for(GraphEntity e : graphManager.getGraphs()){
                for(Node n : e.getEdgeLabels()){
                    renderingsManager.addNode(n);
                }
            }
        }
        else{
            for(GraphEntity e : graphManager.getGraphs()){
                for(Node n: e.getEdgeLabels()){
                    renderingsManager.removeNode(n);
                }
            }
        }
    }

    public RenderingsManager getRenderingsMgr(){
        return renderingsManager;
    }

    public SidebarManager getSidebarMgr(){
        return sidebarManager;
    }

    public void openWorkspace(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Load Workspace");
        alert.setHeaderText("Load a saved workspace");
        alert.setContentText("Are you sure you want to clear your work? Unsaved" + 
                             " changes will be discarded.");
        Optional<ButtonType> response = alert.showAndWait();

        response.ifPresent((ButtonType bt) -> {
            if(bt == ButtonType.OK){
                clear();

                fc.setTitle("Load File");
                File openFile = fc.showOpenDialog(IntegratedGraphing.getPrimaryStage());
//                loadFromFile(openFile);
            }
        });
    }
}
