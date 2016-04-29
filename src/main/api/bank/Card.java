package main.api.bank;

import main.Main;
import main.api.database.HandlerDB;
import main.gui.controller.list.Item;

/**
 * Created by tomas on 4/22/2016.
 */
public class Card implements Item {

    private int cardId;
    private Account account;
    private boolean active;

    public Card(int cardId, Account account, boolean active) {
        this.cardId = cardId;
        this.account = account;
        this.active = active;
    }

    public Card(boolean active){
        this.active = active;
    }

    public Card(int cardId, Account account, int active) {
        this.cardId = cardId;
        this.account = account;
        if(active == 0){
            this.active = false;
        }
        else{
            this.active = true;
        }
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCardId() {
        return cardId;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", account=" + account +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean delete() {
        HandlerDB handlerDB = new HandlerDB(Main.URL,Main.DATABASE,Main.USER,Main.PASS);
        return handlerDB.executeManipulate("DELETE FROM Cards WHERE CardID='"+cardId+"'");
    }
}
