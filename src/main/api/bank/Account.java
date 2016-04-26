package main.api.bank;

import main.Main;
import main.api.database.HandlerDB;
import main.api.person.Client;
import main.gui.controller.list.Item;

/**
 * Created by tomas on 4/22/2016.
 */
public class Account implements Item{

    private int accountId;
    private Client client;
    private float balance;

    public Account(int accountId, Client client, float balance) {
        this.accountId = accountId;
        this.client = client;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public Client getClient() {
        return client;
    }

    public float getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", client=" + client +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean delete() {
        HandlerDB handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
        return handlerDB.executeManipulate("DELETE FROM Accounts WHERE AccountID='"+accountId+"'");
    }
}
