package main.gui.controller;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.Main;
import main.api.database.HandlerDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tomas on 4/18/2016.
 */
public abstract class ActionWindow implements EventHandler{

    protected HandlerDB handlerDB;
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

}
