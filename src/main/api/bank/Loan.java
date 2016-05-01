package main.api.bank;

import main.Main;
import main.api.database.HandlerDB;
import main.api.person.Client;
import main.gui.controller.list.Item;

/**
 * Created by tomas on 5/1/2016.
 */
public class Loan implements Item{

    private int loanId;
    private float amount;
    private float paid;
    private float interest;
    private float totalReturn;
    private Client client;

    public Loan(String loanID, String amount, String paid, String interest, Client client){
        this.loanId = Integer.parseInt(loanID);
        this.amount = Float.parseFloat(amount);
        this.paid = Float.parseFloat(paid);
        this.interest = Float.parseFloat(interest) / 100;
        this.client = client;

        totalReturn = this.amount + this.amount * this.interest;
    }

    public int getLoanId() {
        return loanId;
    }

    public float getAmount() {
        return amount;
    }

    public float getPaid() {
        return paid;
    }

    public float getInterest() {
        return interest;
    }

    public Client getClient() {
        return client;
    }

    public float getTotalReturn() {
        return totalReturn;
    }

    public float getLeftAmount(){
        return  totalReturn - paid;
    }
    @Override
    public boolean delete() {
        HandlerDB handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
        return handlerDB.executeManipulate("DELETE FROM Loans WHERE LoanID='"+ loanId +"'");
    }
}
