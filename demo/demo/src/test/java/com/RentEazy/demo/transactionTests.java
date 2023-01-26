package com.RentEazy.demo;

import com.RentEazy.demo.Dao.JdbcTransactionDao;
import com.RentEazy.demo.Model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class transactionTests {

    /*
    Reference:
    This test uses the JUnit testing framework and the Mockito library to test the JdbcTransactionDao class.
    It creates mock objects for the JdbcTemplate and SqlRowSet, and uses the @InjectMocks annotation to inject these mocks
    into the JdbcTransactionDao object. The test methods use the when() method to specify the behavior of the mocks when certain methods are called,
    and the assertEquals() method to check that the actual results match the expected results.

    The assertEquals method is used to check that the actual results of the JdbcTransactionDao methods match the expected results. For example,
    in the testCreate method, the assertEquals method is used to check that the create method returns true, which is the expected result.

    the test methods also use the "when" method to specify the behavior of the SqlRowSet mock object when certain methods are called, For example, in the
    testGetTransactionsByName method, the "when" method is used to specify that the rowSet.next() method should return true twice and then false when it is called.
     */

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private SqlRowSet rowSet;

    @InjectMocks
    private JdbcTransactionDao jdbcTransactionDao;


    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1000));
        transaction.setForCurrentMonth(true);
        transaction.setPaymentDate(LocalDate.of(2022, 7, 1));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        when(jdbcTemplate.update("INSERT INTO transaction_history(tenant_id, payment_date, amount, for_current_month, is_late_payment) " +
                "VALUES((SELECT tenant_id FROM tenants WHERE tenant_name = ?), ?, ?, ?, ?)", "John Doe", transaction.getPaymentDate(), transaction.getAmount(), transaction.isForCurrentMonth(), transaction.isLatePayment())).thenReturn(1);
        when(jdbcTemplate.update("UPDATE tenants " +
                "SET current_month_paid = true " +
                "WHERE tenant_id = (SELECT tenant_id FROM tenants WHERE tenant_name = ?)", transaction.getAmount(), "John Doe")).thenReturn(1);

        assertEquals(true, jdbcTransactionDao.create(transaction, "John Doe"));
    }

    @Test
    public void testGetTransactionsByName() {
        when(jdbcTemplate.queryForRowSet("SELECT tenant_name, payment_date, amount, for_current_month FROM transaction_history " +
                "INNER JOIN tenants ON transaction_history.tenant_id = tenants.tenant_id", "John Doe")).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, true, false);
        when(rowSet.getBigDecimal("amount")).thenReturn(new BigDecimal(1000));
        when(rowSet.getBoolean("for_current_month")).thenReturn(true);
        when(rowSet.getDate("payment_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2022, 7, 1)));

        ArrayList<Transaction> transactions = jdbcTransactionDao.getTransactionsByName("John Doe");
        assertEquals(2, transactions.size());
    }

    @Test
    public void testListTransactions() {
        when(jdbcTemplate.queryForRowSet("SELECT * FROM transaction_history")).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, true, false);
        when(rowSet.getBigDecimal("amount")).thenReturn(new BigDecimal(1000));
        when(rowSet.getBoolean("for_current_month")).thenReturn(true);
        when(rowSet.getDate("payment_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2022, 7, 1)));

        ArrayList<Transaction> transactions = jdbcTransactionDao.listTransactions();
        assertEquals(2, transactions.size());
    }

    @Test
    public void testMapRowToTransaction() {
        when(rowSet.getBigDecimal("amount")).thenReturn(new BigDecimal(1000));
        when(rowSet.getBoolean("for_current_month")).thenReturn(true);
        when(rowSet.getDate("payment_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2022, 7, 1)));

        Transaction transaction = jdbcTransactionDao.mapRowToTransaction(rowSet);
        assertEquals(new BigDecimal(1000), transaction.getAmount());
        assertEquals(true, transaction.isForCurrentMonth());
        assertEquals(LocalDate.of(2022, 7, 1), transaction.getPaymentDate());
    }

}
