package main.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.api.Login;
import main.api.person.Person;

import java.io.IOException;

public class EmployeeLogin {

    @FXML private Text loginError;
    @FXML private TextField username;
    @FXML private PasswordField password;

    private Person loggedInPerson;

    @FXML private void loginEmployee(ActionEvent event){
        Login loginHandler = new Login(Login.LoginType.EMPLOYEE);
        if(loginHandler.checkEmployeeLogin(username.getText(),password.getText())){
            loggedInPerson = loginHandler.getLoggedInPerson();
            loginError.setText("Welcome "+loggedInPerson.getFirstName());


            try {
                openMainWindow(Login.LoginType.EMPLOYEE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ((Node)(event.getSource())).getScene().getWindow().hide();

        }
        else{
            loginError.setText("Something is wrong!");
        }
    }

    private void openMainWindow(Login.LoginType type) throws IOException {
        Parent root;

        if(type == Login.LoginType.EMPLOYEE){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/employeeMainWindow.fxml"));
            EmployeeMainWindow windowController = new EmployeeMainWindow(loggedInPerson);
            loader.setController(windowController);
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee "+loggedInPerson.getFirstName()+" "+loggedInPerson.getLastName());
            stage.setScene(new Scene(root));
            stage.show();

            windowController.initWindow();
        }
        else{

        }
    }
}
