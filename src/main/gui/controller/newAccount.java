package main.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.api.bank.Account;
import main.api.bank.Card;
import main.api.person.Client;

/**
 * Created by tomas on 4/28/2016.
 */
public class NewAccount extends ActionWindow implements ChangeListener<Boolean>{

    private Client client;
    private DataChangeCallback callback;
    @FXML private CheckBox cardCheck;
    private Card tmpCard;

    public NewAccount(DataChangeCallback callback, Client client) {
        this.callback = callback;
        this.client = client;
    }

    @Override
    public void initWindow() {
        parentScene = cardCheck.getScene();

        if(parentScene.lookup("#saveButton") != null){
            ((Button)parentScene.lookup("#saveButton")).setOnAction(this);
        }

        if(parentScene.lookup("#cancellButton") != null){
            ((Button)parentScene.lookup("#cancellButton")).setOnAction(this);
        }

        if(parentScene.lookup("#ownerField") != null){
            if(client != null){
                ((TextField)parentScene.lookup("#ownerField")).setText(client.toString());
            }
        }

        cardCheck.selectedProperty().addListener(this);
    }

    public void insertCardInfo(boolean active){
        if(parentScene.lookup("#cardProperty") != null){
            if(active){
                ((Text)parentScene.lookup("#cardProperty")).setText("Card is Active");
            }
            else{
                ((Text)parentScene.lookup("#cardProperty")).setText("Card is Disabled");
            }
        }
        tmpCard = new Card(active);
    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(parentScene.lookup("#saveButton"))){
            saveAccount();
        }
        else if(event.getSource().equals(parentScene.lookup("#cancellButton"))){
            closeWindow();
        }
    }

    private void saveAccount() {
        String balance = "0";
        if(parentScene.lookup("#balanceField") != null){
            balance = ((TextField)parentScene.lookup("#balanceField")).getText();
        }
        Account tmpAccount = new Account(client);
        String query = "INSERT INTO Accounts (ClientID,Balance) VALUES ('"+client.getId()+"','"+balance+"')";
        if(handlerDB.executeManipulate(query)){
            tmpAccount.setAccountId(handlerDB.getLastId());
            tmpAccount.setBalance(Float.parseFloat(balance));
            if(tmpCard != null){
                tmpCard.setAccount(tmpAccount);
                int activeField = 0;
                if(tmpCard.isActive()){
                    activeField = 1;
                }
                query = "INSERT INTO Cards (AccountID,Active) VALUES ('"+tmpAccount.getAccountId()+"','"+activeField+"')";
                handlerDB.executeManipulate(query);
            }

            callback.dataUpdate();
            closeWindow();
        }
    }

    public void closingCardWindow(){
        cardCheck.selectedProperty().setValue(false);
    }
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if(newValue){
            openWindow(new NewCard(this),"../layout/newCard.fxml","Set card");
        }
        else{
            closeAllAsocWindows();
        }
    }
}
