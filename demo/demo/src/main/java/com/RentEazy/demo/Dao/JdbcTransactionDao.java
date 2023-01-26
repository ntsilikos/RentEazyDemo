package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Tenant;
import com.RentEazy.demo.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JdbcTransactionDao implements TransactionDao{

    /*
    This class is designed to implement the methods defined in the TransactionDao. In these methods, we can manipulate
    the data in our pgAdmin database. It includes create, grabbing transactions by tenant name, and listing all transactions.

     */


    //Automatically inject an instance of jdbcTemplate, to run its methods off of.
    @Autowired
    public JdbcTemplate jdbcTemplate;


/*
    public JdbcTransactionDao() {
        jdbcTemplate = new JdbcTemplate();
    }

 */

    @Override
    public boolean create(Transaction transaction, String name) {
        String sql = "INSERT INTO transaction_history(tenant_id, payment_date, amount, for_current_month, is_late_payment) " +
                "VALUES((SELECT tenant_id FROM tenants WHERE tenant_name = ?), ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, name, transaction.getPaymentDate(), transaction.getAmount(), transaction.isForCurrentMonth(), transaction.isLatePayment());
        if(transaction.isForCurrentMonth() == false && rowsAffected == 1) {
            String updateTenantSql = "UPDATE tenants " +
                    "SET current_rent_owed = current_rent_owed - ? " +
                    "WHERE tenant_id = (SELECT tenant_id FROM tenants WHERE tenant_name = ?)";
            jdbcTemplate.update(updateTenantSql,transaction.getAmount(), name);
            return true;
        }
        else if(transaction.isForCurrentMonth() == true && rowsAffected == 1) {
            String updateTenantSql = "UPDATE tenants " +
                    "SET current_month_paid = true " +
                    "WHERE tenant_id = (SELECT tenant_id FROM tenants WHERE tenant_name = ?)";
            jdbcTemplate.update(updateTenantSql, name);
            return true;
        }
        else {
            return false;

        }
    }

    @Override
    public boolean delete(int transactionId) {
        String sql = "DELETE FROM transaction_history WHERE transaction_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, transactionId);
        jdbcTemplate.update(sql, transactionId);
        if(rowsAffected == 1) {
            return true;
        }
        else {
            return false;
        }
    }



    @Override
    public ArrayList<Transaction> getTransactionsByName(String name) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT tenant_name, payment_date, amount, for_current_month FROM transaction_history " +
                "INNER JOIN tenants ON transaction_history.tenant_id = tenants.tenant_id " +
                "WHERE transaction_history.tenant_id = (SELECT tenant_id FROM tenants WHERE tenant_name = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        // implement the method to retrieve a tenant by name
        while(results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public ArrayList<Transaction> listTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT *" +
                " FROM transaction_history";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            //			CatCard card = mapRowToCard(results);
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);

        }
        return transactions;
    }

    @Override
    public Transaction mapRowToTransaction(SqlRowSet rowSet) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rowSet.getInt("transaction_id"));
        transaction.setAmount(rowSet.getBigDecimal("amount"));
        transaction.setForCurrentMonth(rowSet.getBoolean("for_current_month"));
        transaction.setPaymentDate(rowSet.getDate("payment_date").toLocalDate());
        return transaction;
    }



}
