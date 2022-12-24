package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Transaction;

public interface TransactionDao {
    boolean create(Transaction transaction, String name);

}
