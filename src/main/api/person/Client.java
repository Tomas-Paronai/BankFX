package main.api.person;

import main.Main;
import main.api.bank.Account;
import main.api.bank.Card;
import main.api.bank.Loan;
import main.api.database.HandlerDB;

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

    private ArrayList<Account> accounts;
    private ArrayList<Card> cards;
    private ArrayList<Loan> loans;

    public Client(String id, String firstName, String lastName, String DOB) {
        super(Integer.parseInt(id), firstName, lastName);
        if(DOB != null){
            this.DOB = Date.valueOf(DOB);
        }
        else{
            this.DOB = Date.valueOf("2000-01-01");
        }

        accounts = new ArrayList<>();
        cards = new ArrayList<>();
        loans = new ArrayList<>();
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

    public ArrayList<Account> getAccount() {
        return accounts;
    }

    public Account getAccount(int id){
        for(Account tmpAccount : accounts){
            if(tmpAccount.getAccountId() == id){
                return tmpAccount;
            }
        }
        return null;
    }

    public void setAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Card> getCard() {
        return cards;
    }

    public Card getCard(int id){
        for(Card tmpCard : cards){
            if(tmpCard.getCardId() == id){
                return tmpCard;
            }
        }
        return null;
    }

    public void setCard(Card card) {
        cards.add(card);
    }

    public void setLoan(Loan loan){
        loans.add(loan);
    }

    public ArrayList<Loan> getLoan() {
        return loans;
    }

    public Loan getLoan(int id){
        for(Loan tmpLoan : loans){
            if(tmpLoan.getLoanId() == id){
                return tmpLoan;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getFirstName()+" "+getLastName();
    }

    public static ArrayList<Client> parseClients(HashMap<String,ArrayList<String>> resultSet){
        ArrayList<Client> arrayOfClients = new ArrayList<>();
        HandlerDB handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);

        int maxCRows = 0;
        for(String tmpKey : resultSet.keySet()){
            maxCRows = resultSet.get(tmpKey).size();
            break;
        }

        for(int cRow = 0; cRow < maxCRows; cRow++){
            Client tmpClient = new Client
                    (resultSet.get("ClientID").get(cRow),resultSet.get("FirstName").get(cRow),resultSet.get("LastName").get(cRow),null);


            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(resultSet.get("DOB").get(cRow));
                tmpClient.setDOB(new Date(utilDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                tmpClient.setDOB(new Date(0,0,0));
            }

            try {
                HashMap<String,ArrayList<String>> accountResult = handlerDB.executeForResult("SELECT * FROM Accounts WHERE ClientID='"+tmpClient.getId()+"'");
                int maxARows = 0;
                for(String tmpAKey : accountResult.keySet()){
                    maxARows = accountResult.get(tmpAKey).size();
                    break;
                }
                for(int aRow = 0; aRow <maxARows; aRow++){
                    Account tmpAccount = new Account(Integer.parseInt(accountResult.get("AccountID").get(aRow)),tmpClient,Float.parseFloat(accountResult.get("Balance").get(aRow)));

                    try{
                        HashMap<String,ArrayList<String>> cardResult = handlerDB.executeForResult("SELECT * FROM Cards WHERE AccountID='"+tmpAccount.getAccountId()+"'");
                        Card tmpCard = new Card(Integer.parseInt(cardResult.get("CardID").get(0)),tmpAccount,Integer.parseInt(cardResult.get("Active").get(0)));
                        tmpClient.setCard(tmpCard);
                    }
                    catch (HandlerDB.DBHandlerException e) {
                        //e.printStackTrace();
                        e.toString();
                    }

                    tmpClient.setAccount(tmpAccount);
                }
            } catch (HandlerDB.DBHandlerException e) {
               // e.printStackTrace();
                e.toString();
            }

            try{
                HashMap<String,ArrayList<String>> contactResult = handlerDB.executeForResult("SELECT * FROM Client_detail WHERE ClientID='"+tmpClient.getId()+"'");
                tmpClient.setDetail(contactResult.get("Email").get(0),contactResult.get("Tel").get(0));
            } catch (HandlerDB.DBHandlerException e) {
                //e.printStackTrace();
                e.toString();
            }

            try{
                HashMap<String,ArrayList<String>> loginResult = handlerDB.executeForResult("SELECT * FROM Client_login WHERE ClientID='"+tmpClient.getId()+"'");
                tmpClient.setUsername(loginResult.get("Username").get(0));
            } catch (HandlerDB.DBHandlerException e) {
                //e.printStackTrace();
                e.toString();
            }

            try{
                HashMap<String, ArrayList<String>> loanResult = handlerDB.executeForResult("SELECT * FROM Loans WHERE ClientID='"+tmpClient.getId()+"'");
                int maxLRows = 0;
                for(String tmpKey : loanResult.keySet()){
                    maxLRows = loanResult.get(tmpKey).size();
                    break;
                }

                for(int lRow = 0; lRow < maxLRows; lRow++){
                    Loan tmpLoan = new Loan(loanResult.get("LoanID").get(lRow), loanResult.get("Amount").get(lRow), loanResult.get("Paid").get(lRow), loanResult.get("Interest").get(lRow), tmpClient);
                    tmpClient.setLoan(tmpLoan);
                }

            } catch (HandlerDB.DBHandlerException e) {
                //e.printStackTrace();
                e.toString();
            }
            //System.out.println(tmpClient.toString()+" Accounts: "+tmpClient.getAccount().size()+" Cards: "+tmpClient.getCard().size());
            arrayOfClients.add(tmpClient);
        }

        return arrayOfClients;
    }

    /*public static Client getClientById(String id, HandlerDB handlerDB){
        HashMap<String,ArrayList<String>> result = handlerDB.executeForResult("SELECT * FROM")
    }*/
}
