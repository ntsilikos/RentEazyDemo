package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Tenant;
import com.RentEazy.demo.Model.Transaction;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;

public interface TransactionDao {
    boolean create(Transaction transaction, String name);

    boolean delete(int transactionId);


    ArrayList<Transaction> getTransactionsByName(String name);

    ArrayList<Transaction> listTransactions();

    Transaction mapRowToTransaction(SqlRowSet rowSet);


}
