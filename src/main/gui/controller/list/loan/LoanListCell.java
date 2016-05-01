package main.gui.controller.list.loan;

import javafx.scene.control.ListCell;
import main.api.bank.Loan;
import main.gui.controller.DataChangeCallback;

/**
 * Created by tomas on 5/1/2016.
 */
public class LoanListCell extends ListCell<Loan> {

    private DataChangeCallback callback;

    public LoanListCell(DataChangeCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void updateItem(Loan item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null){
            setGraphic(null);
        }
        if(item != null){
            LoanItem loanItem = new LoanItem(item,callback);
            setGraphic(loanItem.getContainer());
        }
    }
}
