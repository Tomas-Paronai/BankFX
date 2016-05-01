package main.gui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.api.bank.Account;

import java.util.ArrayList;

/**
 * Created by tomas on 5/1/2016.
 */
public class NewLoan extends ActionWindow {

    private ArrayList<Account> accounts;
    private DataChangeCallback callback;
    @FXML private Text errorText;

    public NewLoan(ArrayList<Account> accounts, DataChangeCallback callback) {
        this.accounts = accounts;
        this.callback = callback;
    }

    @Override
    public void initWindow() {
        parentScene = errorText.getScene();

        if(parentScene.lookup("#saveButton") != null){
            ((Button)parentScene.lookup("#saveButton")).setOnAction(this);
        }

        if(parentScene.lookup("#cancelButton") != null){
            ((Button)parentScene.lookup("#cancelButton")).setOnAction(this);
        }

        if(accounts != null && accounts.size() > 0){
            ((ComboBox<Account>)parentScene.lookup("#accountsComboBox")).getItems().addAll(accounts);
        }
        else{
            errorText.setText("No accounts available!");
        }

    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(parentScene.lookup("#saveButton"))){
            if(saveLoan()){
                callback.dataUpdate();
                closeWindow();
            }
        }
        else if(event.getSource().equals(parentScene.lookup("#cancelButton"))){
            closeWindow();
        }
    }

    private boolean saveLoan() {
        TextField interest = (TextField)parentScene.lookup("#interestField");
        TextField amount = (TextField)parentScene.lookup("#amountField");
        Account selectedAccount = ((ComboBox<Account>)parentScene.lookup("#accountsComboBox")).getSelectionModel().getSelectedItem();
        if(Float.parseFloat(((TextField)parentScene.lookup("#interestField")).getText()) <= 100 && selectedAccount != null){
            String query = "INSERT INTO Loans (ClientID,Amount,Interest) VALUES ('"+selectedAccount.getClient().getId()+"','"+Float.parseFloat(amount.getText())+"','"+Float.parseFloat(interest.getText())+"')";
            if(handlerDB.executeManipulate(query)){
                query = "UPDATE Accounts SET Balance=Balance+"+Float.parseFloat(amount.getText())+" WHERE AccountID="+selectedAccount.getAccountId();
                return handlerDB.executeManipulate(query);
            }
        }
        else{
            errorText.setText("Recheck fields!");
        }
        return false;
    }
}
