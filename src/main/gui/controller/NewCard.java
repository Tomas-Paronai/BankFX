package main.gui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import main.api.bank.Account;

/**
 * Created by tomas on 4/29/2016.
 */
public class NewCard extends ActionWindow {

    @FXML private Text accountId;
    private Account account;
    private NewAccount newAccountWindow;

    public NewCard(NewAccount newAccountWindow){
        this.newAccountWindow = newAccountWindow;
    }

    public NewCard(Account account){
        this.account = account;
    }

    @Override
    public void initWindow() {
        parentScene = accountId.getScene();
        if(account != null){
            accountId.setText(String.valueOf(account.getAccountId()));
        }

        if(parentScene.lookup("#saveButton") != null){
            ((Button)parentScene.lookup("#saveButton")).setOnAction(this);
        }

        if(parentScene.lookup("#cacelButton") != null){
            ((Button)parentScene.lookup("#cacelButton")).setOnAction(this);
        }
    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(parentScene.lookup("#saveButton"))){
            if(newAccountWindow != null){
                newAccountWindow.insertCardInfo(((CheckBox)parentScene.lookup("#isActive")).isSelected());
                closeWindow();
            }
            else if(account != null){
                int activeValue = 0;
                if(((CheckBox)parentScene.lookup("#isActive")).isSelected()){
                    activeValue = 1;
                }
                String query = "INSERT INTO Cards (AccountID,Active) VALUES ('"+account.getAccountId()+"','"+activeValue+"')";
                if(handlerDB.executeManipulate(query)){
                    closeWindow();
                }
            }
        }
        else if(event.getSource().equals(parentScene.lookup("#cancelButton"))){
            if(newAccountWindow != null){
                  newAccountWindow.closingCardWindow();
            }
            closeWindow();
        }
    }
}
