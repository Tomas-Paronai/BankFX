package main.gui.controller;

import javafx.event.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.api.bank.Account;
import main.api.bank.Card;
import main.api.database.HandlerDB;
import main.api.person.Client;
import main.api.person.Employee;
import main.api.person.Person;
import main.gui.controller.list.AccountListCell;
import main.gui.controller.list.CardListCell;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tomas on 4/15/2016.
 */
public class EmployeeMainWindow extends ActionWindow implements DataChangeCallback{

    private Employee loggedInEmployee;
    private Client selectedClient;
    private ArrayList<Client> clients;

    private Scene parent;
    @FXML private Label welcomeMessage;

    public EmployeeMainWindow(Person person) {
        loggedInEmployee = (Employee) person;
    }

    @Override
    public void initWindow(){
        welcomeMessage.setText("Welcome "+loggedInEmployee.getFirstName()+" "+loggedInEmployee.getLastName()+" to main employee window.");
        parent = welcomeMessage.getScene();

        if(parent.lookup("#logoffButton") != null){
            ((Button)parent.lookup("#logoffButton")).setOnAction(this);
        }

        if(parent.lookup("#changePassButton") != null){
            ((Button)parent.lookup("#changePassButton")).setOnAction(this);
        }

        if(parent.lookup("#newClientButton") != null){
            ((Button)parent.lookup("#newClientButton")).setOnAction(this);
        }

        if(parent.lookup("#archiveButton") != null){
            ((Button)parent.lookup("#archiveButton")).setOnAction(this);
        }

        if(parent.lookup("#editButton") != null){
            ((Button)parent.lookup("#editButton")).setOnAction(this);
        }

        if(parent.lookup("#addAccountButton") != null){
            ((Button)parent.lookup("#addAccountButton")).setOnAction(this);
        }

        if(parent.lookup("#addCardButton") != null){
            ((Button)parent.lookup("#addCardButton")).setOnAction(this);
        }

        initComboBox();

    }

    private void initComboBox(){
        ComboBox clientsCombo = (ComboBox) parent.lookup("#clientsCombo");
        clientsCombo.getItems().clear();
        clients = new ArrayList<>();

        try {

            String query = "SELECT * FROM Clients";
            clients = Client.parseClients(handlerDB.executeForResult(query));

        } catch (HandlerDB.DBHandlerException e) {
            e.printStackTrace();
            clientsCombo.getItems().add("empty set");
        }

        if(clients != null){
            clientsCombo.getItems().addAll(clients);
        }

        clientsCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                selectedClient = (Client) newValue;

                Text name = (Text) parent.lookup("#nameField");
                name.setText(((Client)newValue).getFirstName());

                Text surname = (Text) parent.lookup("#surnameField");
                surname.setText(((Client)newValue).getLastName());

                Text dob = (Text) parent.lookup("#dobField");
                dob.setText(((Client)newValue).getDobToString());

                Text email = (Text) parent.lookup("#emailField");
                email.setText(((Client)newValue).getEmail());

                Text tel = (Text) parent.lookup("#telField");
                tel.setText(((Client)newValue).getTel());

                Text username = (Text) parent.lookup("#usernameField");
                username.setText(((Client)newValue).getUsername());

                initLists((Client)newValue);
            }
        });

        if(clients != null && clients.size() > 0){
            clientsCombo.getSelectionModel().select(0);
        }



    }

    private void initLists(Client client){
        ListView<Account> clientAccounts = (ListView<Account>) parent.lookup("#accountsList");
        ListView<Card> clientCards = (ListView<Card>) parent.lookup("#cardsList");
        clientAccounts.getItems().clear();
        clientCards.getItems().clear();

        if(clientAccounts != null && clientCards != null){

            clientAccounts.getItems().addAll(client.getAccount());
            clientCards.getItems().addAll(client.getCard());

            accountListLayout(clientAccounts);
            cardListLayout(clientCards);
        }
    }

    private void accountListLayout(ListView<Account> clientAccounts) {
        clientAccounts.setCellFactory(param -> new AccountListCell(this));
    }

    private void cardListLayout(ListView<Card> clientCards){
        clientCards.setCellFactory(param -> new CardListCell(this));
    }

    private void logOff(ActionEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../layout/employeeLogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("EmployeeLogin");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void changePassword(ActionEvent event){
        Parent root = null;
        ChangePassword changePasswordWindow = new ChangePassword(loggedInEmployee.getId());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/changePassword.fxml"));
            loader.setController(changePasswordWindow);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(root != null){
            Stage stage = new Stage();
            stage.setTitle("Chande password");
            stage.setScene(new Scene(root));
            stage.show();

            changePasswordWindow.initWindow();
        }
    }

    private void newClient(ActionEvent event, Client selected){
        Parent root = null;
        NewClientForm newClientForm = new NewClientForm(this,selected);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/newClientForm.fxml"));
            loader.setController(newClientForm);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(root != null){
            Stage stage = new Stage();
            stage.setTitle("New Client");
            stage.setScene(new Scene(root));
            stage.show();

            newClientForm.initWindow();
        }

    }

    private void archiveClient(){

    }

    private void newAccount(){

    }

    private void newCard(){

    }

    @Override
    public void handle(Event event) {

        if(event.getSource().equals(parent.lookup("#logoffButton"))){
            logOff((ActionEvent) event);
        }
        else if(event.getSource().equals(parent.lookup("#changePassButton"))){
            changePassword((ActionEvent) event);
        }
        else if(event.getSource().equals(parent.lookup("#newClientButton"))){
            newClient((ActionEvent) event, null);
        }
        else if(event.getSource().equals(parent.lookup("#archiveButton"))){
            archiveClient();
        }
        else if(event.getSource().equals(parent.lookup("#editButton"))){
            newClient((ActionEvent) event,selectedClient);
        }
        else if(event.getSource().equals(parent.lookup("#addAccountButton"))){
            //TODO new account
        }
        else if(event.getSource().equals(parent.lookup("#addCardButton"))){
            //TODO new card
        }
    }

    @Override
    public void dataUpdate() {
        ComboBox clientsCombo = (ComboBox) parent.lookup("#clientsCombo");
        clientsCombo.getItems().clear();

        initComboBox();
    }
}
