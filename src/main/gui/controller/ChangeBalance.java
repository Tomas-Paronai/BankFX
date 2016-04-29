package main.gui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.api.bank.Account;
import main.gui.controller.list.Item;

/**
 * Created by tomas on 4/29/2016.
 */
public class ChangeBalance extends ActionWindow {

    private Account account;
    private DataChangeCallback callback;
    private ToggleGroup toggleGroup;
    @FXML private Label balanceLabel;

    public ChangeBalance(Account account,DataChangeCallback callback){
        this.account = account;
        this.callback = callback;
    }

    @Override
    public void initWindow() {
        parentScene = balanceLabel.getScene();

        balanceLabel.setText(String.valueOf(account.getBalance()));

        if(parentScene.lookup("#saveButton") != null){
            ((Button)parentScene.lookup("#saveButton")).setOnAction(this);
        }
        if(parentScene.lookup("#cancelButton") != null){
            ((Button)parentScene.lookup("#cancelButton")).setOnAction(this);
        }

        if(parentScene.lookup("#withdraw") != null && parentScene.lookup("#insert") != null){
            toggleGroup = new ToggleGroup();
            ((RadioButton)parentScene.lookup("#withdraw")).setToggleGroup(toggleGroup);
            ((RadioButton)parentScene.lookup("#insert")).setToggleGroup(toggleGroup);

            ((RadioButton)parentScene.lookup("#withdraw")).selectedProperty().setValue(true);
        }
    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(parentScene.lookup("#saveButton")) && toggleGroup != null){
            float difference = Float.parseFloat(((TextField)parentScene.lookup("#amountField")).getText());
            if(toggleGroup.getSelectedToggle().equals(parentScene.lookup("#withdraw"))){
                difference *=-1;
            }
            String query = "UPDATE Accounts SET Balance=Balance+'"+difference+"' WHERE AccountID='"+account.getAccountId()+"'";
            if(handlerDB.executeManipulate(query)){
                callback.dataUpdate();
                closeWindow();
            }
        }
        else if(event.getSource().equals(parentScene.lookup("#cancelButton"))){
            closeWindow();
        }
    }
}
