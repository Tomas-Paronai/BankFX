package main.api.person;

import main.api.bank.Account;
import main.api.bank.Card;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tomas on 4/15/2016.
 */
public class Client extends Person {

    private Date DOB;
    private String email;
    private String tel;
    private String username;

    private Account account;
    private Card card;

    public Client(String id, String firstName, String lastName, String DOB) {
        super(Integer.parseInt(id), firstName, lastName);
        if(DOB != null){
            this.DOB = Date.valueOf(DOB);
        }
        else{
            this.DOB = Date.valueOf("2000-01-01");
        }
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getDobToString(){
        return DOB.toString();
    }

    public void setDetail(String email, String tel){
        this.email = email;
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return getFirstName()+" "+getLastName();
    }

    public static ArrayList<Client> parseClients(HashMap<String,ArrayList<String>> resultSet){
        ArrayList<Client> arrayOfClients = new ArrayList<>();

        int maxRows = 0;
        for(String tmpKey : resultSet.keySet()){
            maxRows = resultSet.get(tmpKey).size();
            break;
        }

        for(int row = 0; row < maxRows; row++){
            Client tmpClient = new Client
                    (resultSet.get("ClientID").get(row),resultSet.get("FirstName").get(row),resultSet.get("LastName").get(row),null);


            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(resultSet.get("DOB").get(row));
                tmpClient.setDOB(new Date(utilDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                tmpClient.setDOB(new Date(0,0,0));
            }

            Account tmpAccount = new Account(Integer.parseInt(resultSet.get("AccountID").get(row)),tmpClient,Float.parseFloat(resultSet.get("Balance").get(row)));
            tmpClient.setAccount(tmpAccount);
            tmpClient.setDetail(resultSet.get("Email").get(row),resultSet.get("Tel").get(row));
            tmpClient.setUsername(resultSet.get("Username").get(row));

            if(resultSet.get("CardID").get(row) != null){
                tmpClient.setCard(new Card(Integer.parseInt(resultSet.get("CardID").get(row)),tmpAccount,Integer.parseInt(resultSet.get("Active").get(row))));
            }


            arrayOfClients.add(tmpClient);
        }

        return arrayOfClients;
    }

    /*public static Client getClientById(String id, HandlerDB handlerDB){
        HashMap<String,ArrayList<String>> result = handlerDB.executeForResult("SELECT * FROM")
    }*/
}
