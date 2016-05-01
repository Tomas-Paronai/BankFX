package main.gui.controller.list.loan;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.api.bank.Loan;
import main.gui.controller.DataChangeCallback;
import main.gui.controller.list.Item;
import main.gui.controller.list.ItemCell;

/**
 * Created by tomas on 5/1/2016.
 */
public class LoanItem extends ItemCell{

    @FXML private Pane loanContainer;
    @FXML private Text amountField;
    @FXML private Text interestField;
    @FXML private Text leftField;
    @FXML private Button insertButton;
    @FXML private Button deleteButton;
    private Loan loan;

    public LoanItem(Loan loan, DataChangeCallback callback) {
        super(loan, callback, "loanItem.fxml");
        this.loan = loan;
        insertData();
    }

    @Override
    public void insertData() {
        this.amountField.setText(String.valueOf(loan.getAmount()));
        this.interestField.setText(String.valueOf(loan.getInterest()));
        this.leftField.setText(String.valueOf(loan.getLeftAmount()));
        this.insertButton.setOnAction(this);
        this.deleteButton.setOnAction(this);
    }

    @Override
    public Pane getContainer() {
        return loanContainer;
    }


    @Override
    public void handle(Event event) {
        if(event.getSource().equals(insertButton)){
            //TODO open change
        }
        else if(event.getSource().equals(deleteButton)){
            if(loan.delete()){
                callback.dataUpdate();
            }
        }
    }
}
