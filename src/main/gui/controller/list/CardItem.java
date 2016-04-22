package main.gui.controller.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by tomas on 4/22/2016.
 */
public class CardItem {

    @FXML private Pane cardContainer;
    @FXML private Label accountNumber;
    @FXML private Label accountOwner;
    @FXML private Label cardBlock;

    public CardItem() {
        System.out.println(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/list/cardItem.fxml"));
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void insertData(String accountNumber, String accountOwner, boolean cardBlock){
        this.accountNumber.setText(accountNumber);
        this.accountOwner.setText(accountOwner);
        if(!cardBlock){
            this.cardBlock.setText("Card is blocked!");
        }
        else{
            this.cardBlock.setText("");
        }
    }

    public Pane getCardContainer() {
        return cardContainer;
    }
}
