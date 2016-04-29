package main.gui.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import main.api.database.HandlerDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tomas on 4/18/2016.
 */
public abstract class ActionWindow implements EventHandler{

    protected HandlerDB handlerDB;
    protected Scene parentScene;
    private ArrayList<ActionWindow> assocWindows;
    private Stage stage;

    public ActionWindow() {
        handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
    }

    public abstract void initWindow();

    public HashMap<String,ArrayList<String>> getResult(String selectQuery){
        try {
            return handlerDB.executeForResult(selectQuery);
        } catch (HandlerDB.DBHandlerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean openWindow(ActionWindow window, String fmxlPath, String title){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fmxlPath));
            loader.setController(window);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

        if(assocWindows == null){
            assocWindows = new ArrayList<>();
        }
        window.setStage(stage);
        assocWindows.add(window);

        window.initWindow();
        return true;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean hasAssocWindows(){
        if(assocWindows != null){
            return true;
        }
        return false;
    }

    public void closeWindow(){
        closeAllAsocWindows();
        if(stage != null){
            stage.hide();
        }
    }

    public void closeAllAsocWindows(){
        if(hasAssocWindows()){
            for(ActionWindow tmpWindow : assocWindows){
                tmpWindow.closeWindow();
            }
            assocWindows.clear();
        }
    }

    public void closeAsocWindow(ActionWindow window){
        if(hasAssocWindows()){
            for(ActionWindow tmpWindow : assocWindows){
                if(tmpWindow.equals(window)){
                    tmpWindow.closeWindow();
                    assocWindows.remove(window);
                    return;
                }
            }
        }
    }
}
