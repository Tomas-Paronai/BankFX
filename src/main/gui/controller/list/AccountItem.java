package main.gui.controller.list;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.api.bank.Account;
import main.gui.controller.ChangeBalance;
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
    @FXML private Button changeBalanceButton;
    private Item item;
    private Account account;
    private DataChangeCallback callback;

    public AccountItem(Account item, DataChangeCallback callback) {
        this.item = item;
        this.account = item;
        this.callback = callback;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/list/accountItem.fxml"));
        loader.setController(this);

        try{
            loader.load();
            insertData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(){
        this.accountNumber.setText(String.valueOf(account.getAccountId()));
        this.accountBalance.setText(String.valueOf(account.getBalance()));
        this.deleteAccountButton.setOnAction(this);
        this.changeBalanceButton.setOnAction(this);
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
        else if(event.getSource().equals(changeBalanceButton)){
            Parent root = null;
            ChangeBalance changeBalanceWindow = new ChangeBalance(account,callback);
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/changeBalance.fxml"));
                loader.setController(changeBalanceWindow);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(root != null){
                Stage stage = new Stage();
                stage.setTitle("Change balance");
                stage.setScene(new Scene(root));
                stage.show();

                changeBalanceWindow.setStage(stage);
                changeBalanceWindow.initWindow();
            }
        }
    }
}
