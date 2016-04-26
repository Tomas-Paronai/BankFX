package main.gui.controller.list;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.api.bank.Account;
import main.gui.controller.DataChangeCallback;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class AccountItem implements EventHandler {

    @FXML private Pane accountContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountBalance;
    @FXML private Label accountOwner;
    @FXML private Button deleteAccountButton;
    private Item item;
    private DataChangeCallback callback;

    public AccountItem(Item item, DataChangeCallback callback) {
        this.item = item;
        this.callback = callback;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/list/accountItem.fxml"));
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String accountNumber, String accountBalance){
        this.accountNumber.setText(accountNumber);
        this.accountBalance.setText(accountBalance);
        this.deleteAccountButton.setOnAction(this);
    }

    public Pane getAccountContainer() {
        return accountContainer;
    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(deleteAccountButton)){
            if(item.delete()){
                callback.dataUpdate();
            }
        }
    }
}
