package main.gui.controller.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class AccountItem {

    @FXML private Pane accountContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountBalance;
    @FXML private Label accountOwner;

    public AccountItem() {
        System.out.println(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/list/accountItem.fxml"));
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String accountNumber, String accountOwner, String accountBalance){
        this.accountNumber.setText(accountBalance);
        this.accountBalance.setText(accountBalance);
        this.accountOwner.setText(accountOwner);
    }

    public Pane getAccountContainer() {
        return accountContainer;
    }
}
