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
import GraphTheory.IntegratedGraphing;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.UIComponents.GraphManager;
import GraphTheory.UIComponents.MultiSelectionDialog;
import GraphTheory.UIComponents.RenderingsManager;
import GraphTheory.UIComponents.SidebarManager;
import java.io.File;
import java.util.Iterator;
import java.util.Optional;
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
    
    public Headquarters(RenderingsManager r, GraphManager g, SidebarManager s,
                        FileManager f){
        renderingsManager = r;
        graphManager = g;
        sidebarManager = s;
        fileManager = f;
    }

    public void setCurrentGraph(GraphEntity e){
        if(!e.equals(getCurrentGraph())){
            graphManager.setCurrentGraph(e);
            sidebarManager.setCurrentGraph(e);
        }
    }

    public void setCurrentGraph(int index){
        setCurrentGraph(graphManager.get(index));
    }
    
    public void setCurrentGraph(String tag){
        GraphEntity e = graphManager.get(tag);
        if(e!=null)
            setCurrentGraph(e);
    }
    
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

    public void addGraph(Graph g){
        addGraph(new GraphEntity(g));
    }
     
    public void addGraph(String name, Graph g){
        addGraph(new GraphEntity(name,g));        
    }

    public void addGraph(GraphEntity e){
        graphManager.addGraph(e);
        sidebarManager.addGraph(e);
    }

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

    public void removeGraph(GraphEntity e){
        Logger.log("Removing graph: " + e.getName());
        renderingsManager.removeNode(e.getGraph().getCircle());
        renderingsManager.removeAll(e.getGraph().getContents());
        graphManager.removeGraph(e);
        sidebarManager.removeGraph(e);
    }

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

    public void pointerPressed(MouseEvent event, Translatable t){
        renderingsManager.removeGlow();
        if(t instanceof Graph)
            setCurrentGraph((Graph)t);
        else if(t instanceof GraphVertex)
            setCurrentGraph(((GraphVertex)t).getParent());
        else if(t instanceof GraphEdge)
            setCurrentGraph(((GraphEdge)t).getParent());
    }

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

    public void edgeDeleted(GraphEdge edge){
        graphManager.removeEdge(edge);
    }

    public void vertexDeleted(GraphVertex vertex){
        graphManager.removeVertex(vertex);
    }

    public void vertexAdded(double x, double y){
        graphManager.addVertex(x,y);
    }

    public void complementGraph(){
        ChoiceDialog<String> selectionDialog = new ChoiceDialog<>(
             graphManager.curGraphEntity.getName(), graphManager.getGraphNames());
        selectionDialog.setTitle("Complement");
        selectionDialog.setHeaderText("Select which graph to complement:");

        Optional<String> result = selectionDialog.showAndWait();
        result.ifPresent((String target) -> {
            graphManager.get(target).getGraph().complement();
        });
    }

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

    public GraphEntity getCurrentGraph(){
        return graphManager.getCurrentGraph();
    }

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

    public void saveToFile(){
        fileManager.saveToFile(graphManager.getGraphs());
    }

    public void saveAsToFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file as:");
        fc.setInitialDirectory(new File(IntegratedGraphing.dataDirectory));
        fc.getExtensionFilters().addAll(
            new ExtensionFilter("Graph Workspace Files", "*.grph"),
            new ExtensionFilter("All Files", "*.*"));
        File newSave = fc.showSaveDialog(IntegratedGraphing.getPrimaryStage());

        fileManager.saveToFile(newSave, graphManager.getGraphs());
    }

    public void clear(){
        Logger.log("Clearing workspace contents.");
        Logger.log(Integer.toString(graphManager.getGraphs().size()));
        Iterator<GraphEntity> iter = graphManager.getGraphs().iterator();
        while(iter.hasNext()){ //enhanced for loop was throwing concurrent modification errors
            removeGraph(iter.next());
        }
        Logger.log("Finished clearing workspace contents.");
    }

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
}
