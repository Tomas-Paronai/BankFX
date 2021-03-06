package main.gui.controller.list.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.Main;
import main.api.bank.Card;
import main.api.database.HandlerDB;
import main.gui.controller.DataChangeCallback;
import main.gui.controller.list.Item;
import main.gui.controller.list.ItemCell;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class CardItem extends ItemCell implements ChangeListener<Boolean> {



    @FXML private Pane cardContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountOwner;
    @FXML private Label cardBlock;
    @FXML private Button deleteCardButton;
    @FXML private CheckBox isActive;
    private Card card;

    public CardItem(Card item, DataChangeCallback callback) {
        super(item,callback,"cardItem.fxml");
        this.card = item;
        insertData();
    }

    @Override
    public void insertData(){
        this.accountNumber.setText(String.valueOf(card.getAccount().getAccountId()));
        this.accountOwner.setText(card.getAccount().getClient().toString());
        isActive.selectedProperty().setValue(card.isActive());
        if(!card.isActive()){
            this.cardBlock.setText("Card is blocked!");
        }
        else{
            this.cardBlock.setText("");
        }

        isActive.selectedProperty().addListener(this);
        deleteCardButton.setOnAction(this);
    }

    @Override
    public Pane getContainer() {
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

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        HandlerDB handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
        int activeValue = 0;
        if(newValue){
            activeValue = 1;
            cardBlock.setText("");
        }
        else{
            cardBlock.setText("Card is blocked!");
        }
        String query = "UPDATE Cards SET Active='"+activeValue+"' WHERE CardID='"+card.getCardId()+"'";
        handlerDB.executeManipulate(query);
    }
}
