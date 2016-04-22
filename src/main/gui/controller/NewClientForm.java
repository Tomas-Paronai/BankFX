package main.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.api.database.HandlerDB;
import main.api.person.Client;

/**
 * Created by tomas on 4/19/2016.
 */
public class NewClientForm extends ActionWindow {

    private Scene parent;
    private DataChangeCallback callback;
    private Client selectedClient;

    @FXML private Text messageText;

    public NewClientForm(DataChangeCallback callback, Client selected) {
        this.callback = callback;
        selectedClient = selected;
    }

    private void saveAll(ActionEvent event) throws HandlerDB.DBHandlerException {

        if(selectedClient == null && ((TextField)parent.lookup("#surnameField")).getText() != "" && ((TextField)parent.lookup("#nameField")).getText() != ""){

            if(handlerDB.executeManipulate("INSERT INTO clients (firstname,lastname) VALUES " +
                "('"+((TextField)parent.lookup("#nameField")).getText()+"','"+((TextField)parent.lookup("#surnameField")).getText()+"')")){

                //client_detail
                if(((TextField)parent.lookup("#emailField")).getText() != "" || ((TextField)parent.lookup("#telField")).getText() != ""){
                    handlerDB.prepareStatement("INSERT INTO client_detail (clientid,email,tel) VALUES (?,?,?)");
                    handlerDB.updateStatement(String.valueOf(handlerDB.getLastId()),((TextField)parent.lookup("#emailField")).getText(),((TextField)parent.lookup("#telField")).getText());
                    handlerDB.executeStatement();
                }

                //client_login
                if(((TextField)parent.lookup("#usernameField")).getText() != "" && ((TextField)parent.lookup("#passwordField")).getText() != ""){
                    handlerDB.prepareStatement("INSERT INTO client_login (clientid,username,password) VALUES (?,?,?)");
                    handlerDB.updateStatement(String.valueOf(handlerDB.getLastId()),((TextField)parent.lookup("#usernameField")).getText(),((TextField)parent.lookup("#passwordField")).getText());
                    handlerDB.executeStatement();
                }

                callback.dataUpdate();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }

        }
        else if(selectedClient != null){
            handlerDB.executeManipulate("UPDATE clients SET firstname='"+((TextField)parent.lookup("#nameField")).getText()+"', lastname='"+((TextField)parent.lookup("#surnameField")).getText()+
                    "' WHERE clientid='"+selectedClient.getId()+"'");
            handlerDB.executeManipulate("UPDATE client_detail SET email='"+((TextField)parent.lookup("#emailField")).getText()+"', tel='"+((TextField)parent.lookup("#telField")).getText()+
                    "' WHERE clientid='"+selectedClient.getId()+"'");
            handlerDB.executeManipulate("UPDATE client_login SET username='"+((TextField)parent.lookup("#usernameField")).getText()+"' WHERE clientid='"+selectedClient.getId()+"'");

            callback.dataUpdate();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

        //TODO parse date picker




    }

    @Override
    public void initWindow() {

        if(messageText != null){
            parent = messageText.getScene();

            ((Button)parent.lookup("#saveButton")).setOnAction(this);
        }

        if(selectedClient != null){
            ((TextField)parent.lookup("#nameField")).setText(selectedClient.getFirstName());
            ((TextField)parent.lookup("#surnameField")).setText(selectedClient.getLastName());
            ((TextField)parent.lookup("#emailField")).setText(selectedClient.getEmail());
            ((TextField)parent.lookup("#telField")).setText(selectedClient.getTel());
            ((TextField)parent.lookup("#usernameField")).setText(selectedClient.getUsername());
        }

    }

    @Override
    public void handle(Event event) {
        if(event.getSource().equals(parent.lookup("#saveButton"))){
            try {
                saveAll((ActionEvent) event);
            } catch (HandlerDB.DBHandlerException e) {
                e.printStackTrace();
            }
        }
    }
}
