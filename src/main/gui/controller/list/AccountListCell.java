package main.gui.controller.list;

import javafx.scene.control.ListCell;
import main.api.bank.Account;
import main.gui.controller.DataChangeCallback;

/**
 * Created by tomas on 4/22/2016.
 */
public class AccountListCell extends ListCell<Account> {

    private DataChangeCallback callback;

    public AccountListCell(DataChangeCallback callback){
        this.callback = callback;
    }

    @Override
    protected void updateItem(Account item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item==null){
            setGraphic(null);
        }
        if(item != null){
            AccountItem accountItem = new AccountItem(item,callback);
            accountItem.insertData(String.valueOf(item.getAccountId()),String.valueOf(item.getBalance()));
            setGraphic(accountItem.getAccountContainer());
        }
    }
}
