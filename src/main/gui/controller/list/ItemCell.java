package main.gui.controller.list;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import main.gui.controller.DataChangeCallback;

import java.io.IOException;

/**
 * Created by tomas on 5/1/2016.
 */
public abstract class ItemCell implements EventHandler {

    protected DataChangeCallback callback;
    protected Item item;
    private final String origPath = "../../../layout/list/";

    public ItemCell(Item item, DataChangeCallback callback, String path) {
        this.callback = callback;
        this.item = item;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(origPath + path));
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void insertData();
    public abstract Pane getContainer();
}
