package com.yourfounds.service.impl;

import com.yourfounds.dao.AccountDao;
import com.yourfounds.dao.ExpenseDao;
import com.yourfounds.entity.Account;
import com.yourfounds.entity.Expense;
import com.yourfounds.service.ExpenseService;
import com.yourfounds.util.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public void addExpense(Expense expense) {
        Account account = expense.getAccount();
        if (expense.getCategory().isIncome()){
            account.plus(expense.getSum());
        } else {
            account.substract(expense.getSum());
        }

        accountDao.update(account);
        expenseDao.save(expense);
    }

    @Override
    @Transactional
    public List<Expense> getAllExpenses() {
        List <Expense> expenses = expenseDao.getAll();
        Collections.sort(expenses);
        return expenses;
    }

    @Override
    @Transactional
    public Expense getExpenseById(int id) {
        return expenseDao.get(id);
    }

    @Override
    @Transactional
    public List<Expense> getExpensesDuringCurrentMonth() {
        List <Expense> expenses = expenseDao.getAllBetweenDates(Calculation.getStartOfCurrentMonth(), Calculation.getEndOfCurrentMonth());
        Collections.sort(expenses);
        return expenses;
    }

    @Override
    @Transactional
    public void updateExpense(Expense expense) {
        expenseDao.update(expense);
    }

    @Override
    @Transactional
    public void deleteExpenseById(int id) {
        Expense expense = expenseDao.get(id);
        Account account = expense.getAccount();
        account.plus(expense.getSum());
        accountDao.update(account);
        expenseDao.delete(id);
    }

    @Override
    public String getCurrentMonthName() {
        return Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

//    private Date getFirstDayOfCurrentMonth(){
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//        System.out.println(cal.toString());
//        return cal.getTime();
//    }
//
//    private Date getLastDayOfCurrentMonth(){
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        System.out.println(cal.toString());
//        return cal.getTime();
//    }
}
