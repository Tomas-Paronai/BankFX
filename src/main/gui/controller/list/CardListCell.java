package main.gui.controller.list;

import javafx.scene.control.ListCell;
import main.api.bank.Card;

/**
 * Created by tomas on 4/22/2016.
 */
public class CardListCell extends ListCell<Card> {

    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null){
            CardItem cardItem = new CardItem();
            cardItem.insertData(String.valueOf(item.getAccount().getAccountId()),item.getAccount().getClient().toString(),item.isActive());
            setGraphic(cardItem.getCardContainer());
        }
    }
}
