/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import GraphTheory.IntegratedGraphing;
import GraphTheory.UIComponents.GraphEntity;
import GraphTheory.Utility.StorageClasses.GraphList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 *
 * @author Thayer
 */
public class FileManager {
    String dir;

    public FileManager(String fileDirectory){
        dir = fileDirectory;
    }

    public void saveToFile(String fileOut, ArrayList<GraphEntity> graphs){
        saveToFile(new File(dir+fileOut), graphs);
    }

    public void saveToFile(File destFile, ArrayList<GraphEntity> graphs){
        Thread t = new Thread(() -> {
            try {
                if(!destFile.exists())
                    destFile.createNewFile();
            } catch (IOException ex) {
                Logger.log("Save aborted, unable to access file.");
                return;
            }

            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(destFile))){
                GraphList gl = new GraphList(graphs);
                out.writeObject(gl);
                out.close();
                Logger.log("Workspace saved to " + destFile.toString());
            } catch (IOException ex) {
                Logger.log(ex.toString());
            }
        });
        t.start();
    }

    public void saveToFile(ArrayList<GraphEntity> graphs){
        saveToFile("autosave.grph", graphs);
    }

    public boolean loadFromFile(String fileIn){
        Logger.log("Begining load from file.");

        if(!fileIn.endsWith(".grph"))
            fileIn = fileIn + ".grph";

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileIn))){
            GraphList gl = (GraphList) in.readObject();
            ArrayList<GraphEntity> entities = gl.getList();

            for(GraphEntity e : entities){
                IntegratedGraphing.getHQ().addGraph(e);
            }
            return true;
        } catch (FileNotFoundException ex){
            Logger.log("Aborting from load, file does not exist.");
            return false;
        } catch (IOException ex){
            Logger.log("Aborting from load, unable to read file.");
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.log("Aborting from load, class not found.");
            return false;
        }
    }

    public boolean loadFromFile(){
        return loadFromFile(IntegratedGraphing.dataDirectory + File.separator + "autosave");
    }

    public static void ensureExists(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            f.mkdir();
        }
    }
}
