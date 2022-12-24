package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransactionDao implements TransactionDao{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao() {
        jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public boolean create(Transaction transaction, String name) {
        String sql = "INSERT INTO transaction_history(tenant_id, payment_date, amount) " +
                "VALUES((SELECT tenant_id FROM tenants WHERE tenant_name = ?), ?, ?)";
        return jdbcTemplate.update(sql, name, transaction.getPaymentDate(), transaction.getAmount()) == 1;
    }

}
