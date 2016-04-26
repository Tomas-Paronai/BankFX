package main.gui.controller.list;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.api.bank.Card;
import main.gui.controller.DataChangeCallback;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class CardItem implements EventHandler {



    @FXML private Pane cardContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountOwner;
    @FXML private Label cardBlock;
    @FXML private Button deleteCardButton;
    private Item item;
    private DataChangeCallback callback;

    public CardItem(Item item, DataChangeCallback callback) {
        this.item = item;
        this.callback = callback;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/list/cardItem.fxml"));
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void insertData(String accountNumber, String accountOwner, boolean cardBlock){
        this.accountNumber.setText(accountNumber);
        this.accountOwner.setText(accountOwner);
        if(!cardBlock){
            this.cardBlock.setText("Card is blocked!");
        }
        else{
            this.cardBlock.setText("");
        }

        deleteCardButton.setOnAction(this);
    }

    public Pane getCardContainer() {
        return cardContainer;
    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(deleteCardButton)){
            if(item.delete()){
                callback.dataUpdate();
            }
        }
    }
}
