package main.api.bank;

/**
 * Created by tomas on 4/22/2016.
 */
public class Card {

    private int cardId;
    private Account account;
    private boolean active;

    public Card(int cardId, Account account, boolean active) {
        this.cardId = cardId;
        this.account = account;
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
}
