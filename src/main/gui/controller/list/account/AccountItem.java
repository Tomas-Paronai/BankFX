package main.gui.controller.list.account;

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
import main.gui.controller.list.Item;
import main.gui.controller.list.ItemCell;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class AccountItem extends ItemCell {

    @FXML private Pane accountContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountBalance;
    @FXML private Label accountOwner;
    @FXML private Button deleteAccountButton;
    @FXML private Button changeBalanceButton;
    private Account account;

    public AccountItem(Account item, DataChangeCallback callback) {
        super(item,callback,"accountItem.fxml");
        this.account = item;
        insertData();
    }

    @Override
    public void insertData(){
        this.accountNumber.setText(String.valueOf(account.getAccountId()));
        this.accountBalance.setText(String.valueOf(account.getBalance()));
        this.deleteAccountButton.setOnAction(this);
        this.changeBalanceButton.setOnAction(this);
    }

    @Override
    public Pane getContainer() {
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
