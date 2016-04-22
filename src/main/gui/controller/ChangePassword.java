package main.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Main;
import main.api.database.HandlerDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tomas on 4/18/2016.
 */
public class ChangePassword extends ActionWindow{

    private int id;
    private HandlerDB handlerDB;
    @FXML private Button confirmButton;


    public ChangePassword(int id) {
        this.id = id;
        handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
    }

    @Override
    public void initWindow(){
        if(confirmButton != null){
            confirmButton.setOnAction(this);
        }
    }

    private void changePassword(ActionEvent event) {
        TextField oldPassword = (TextField) confirmButton.getScene().lookup("#oldPassword");
        TextField newPassword = (TextField) confirmButton.getScene().lookup("#newPassword");
        TextField newPasswordRepeat = (TextField) confirmButton.getScene().lookup("#newPasswordRepeat");

        if(checkUser(oldPassword.getText()) && newPassword.getText().equals(newPasswordRepeat.getText()) && !newPassword.getText().equals("")){
            handlerDB.executeManipulate("UPDATE employee_login SET Password='"+newPassword.getText()+"' WHERE EmployeeID='"+id+"'");
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

    }

    private boolean checkUser(String oldPassword){
        HashMap<String,ArrayList<String>> result = getResult("SELECT * FROM employee_login WHERE EmployeeID='"+id+"' and Password='"+oldPassword+"'");
        if(result != null){
            return true;
        }
        return false;
    }

    @Override
    public void handle(Event event) {
        changePassword((ActionEvent) event);
    }
}
