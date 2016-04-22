package main.gui.controller.list;

import javafx.scene.control.ListCell;
import main.api.bank.Account;

/**
 * Created by tomas on 4/22/2016.
 */
public class AccountListCell extends ListCell<Account> {

    @Override
    protected void updateItem(Account item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null){
            AccountItem accountItem = new AccountItem();
            accountItem.insertData(String.valueOf(item.getAccountId()),item.getClient().toString(),String.valueOf(item.getBalance()));
            setGraphic(accountItem.getAccountContainer());
        }
    }
}
