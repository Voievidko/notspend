package com.yourfounds.dao;

import com.yourfounds.entity.Account;

public interface AccountDao extends CrudDao<Account> {
    boolean isAccountHaveRelations(int id);
    int replaceAccountInAllExpenses(int fromAccountId, int toAccountId);
}
