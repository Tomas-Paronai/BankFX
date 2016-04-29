package main.gui.controller.list;

import javafx.scene.control.ListCell;
import main.api.bank.Card;
import main.gui.controller.DataChangeCallback;

/**
 * Created by tomas on 4/22/2016.
 */
public class CardListCell extends ListCell<Card> {

    private DataChangeCallback callback;

    public CardListCell(DataChangeCallback callback){
        this.callback = callback;
    }

    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item==null){
            setGraphic(null);
        }
        if(item != null){
            CardItem cardItem = new CardItem(item,callback);
            setGraphic(cardItem.getCardContainer());
        }
    }
}
