package com.RentEazy.demo;

import com.RentEazy.demo.Dao.JdbcTenantDao;
import com.RentEazy.demo.Model.Tenant;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class tenantTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcTenantDao jdbcTenantDao;

    private Tenant tenant;

    @Before
    public void setUp() {
        tenant = new Tenant();
        tenant.setTenantName("John Smith");
        tenant.setTenantPhone("555-555-5555");
        tenant.setMonthlyPayment(new BigDecimal("1000.00"));
        tenant.setCurrentRentOwed(new BigDecimal("0.00"));
        tenant.setHasPet(false);
        tenant.setMoveInDate(LocalDate.of(2020, 1, 1));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        // Given
        String sql = "INSERT INTO tenants(tenant_name, tenant_phone, monthly_payment, current_rent_owed, has_pet, move_in_date, security_deposit, current_month_paid)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(sql, tenant.getTenantName(), tenant.getTenantPhone(), tenant.getMonthlyPayment(), tenant.getCurrentRentOwed(),
                tenant.isHasPet(), tenant.getMoveInDate(), tenant.getSecurityDeposit(), tenant.isCurrentMonthPaid())).thenReturn(1);

        // When
        boolean result = jdbcTenantDao.create(tenant);

        // Then
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        // Given
        String sql = "DELETE FROM tenants WHERE tenant_name = ?";
        when(jdbcTemplate.update(sql, tenant.getTenantName())).thenReturn(1);

        // When
        boolean result = jdbcTenantDao.delete(tenant.getTenantName());

        // Then
        assertTrue(result);
    }

    @Test
    public void testListTenants() {
        // Given
        String sql = "SELECT tenant_name, tenant_phone, monthly_payment, current_rent_owed, has_pet, move_in_date" +
                " FROM tenants";
        SqlRowSet results = mock(SqlRowSet.class);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("tenant_name")).thenReturn(tenant.getTenantName());
        when(results.getString("tenant_phone")).thenReturn(tenant.getTenantPhone());
        when(results.getBigDecimal("monthly_payment")).thenReturn(tenant.getMonthlyPayment());
        when(results.getBigDecimal("current_rent_owed")).thenReturn(tenant.getCurrentRentOwed());
        when(results.getBoolean("has_pet")).thenReturn(tenant.isHasPet());
        when(results.getDate("move_in_date")).thenReturn(Date.valueOf(tenant.getMoveInDate()));
        when(jdbcTemplate.queryForRowSet(sql)).thenReturn(results);
        // When
        ArrayList<Tenant> tenants = jdbcTenantDao.listTenants();

        // Then
        assertEquals(2, tenants.size());
        assertEquals(tenant, tenants.get(0));
    }

    @Test
    public void testGetTenantByName() {
        // Given
        String sql = "SELECT tenant_name, tenant_phone, monthly_payment, current_rent_owed, has_pet, move_in_date FROM tenants WHERE tenant_name = ?";
        SqlRowSet results = mock(SqlRowSet.class);
        when(results.next()).thenReturn(true, false);
        when(results.getString("tenant_name")).thenReturn(tenant.getTenantName());
        when(results.getString("tenant_phone")).thenReturn(tenant.getTenantPhone());
        when(results.getBigDecimal("monthly_payment")).thenReturn(tenant.getMonthlyPayment());
        when(results.getBigDecimal("current_rent_owed")).thenReturn(tenant.getCurrentRentOwed());
        when(results.getBoolean("has_pet")).thenReturn(tenant.isHasPet());
        when(results.getDate("move_in_date")).thenReturn(Date.valueOf(tenant.getMoveInDate()));
        when(jdbcTemplate.queryForRowSet(sql, tenant.getTenantName())).thenReturn(results);

        // When
        ArrayList<Tenant> tenants = jdbcTenantDao.getTenantByName(tenant.getTenantName());

        // Then
        assertEquals(1, tenants.size());
        assertEquals(tenant, tenants.get(0));
    }

    @Test
    public void testMapRowToTenant() {
        // Given
        SqlRowSet rowSet = mock(SqlRowSet.class);
        when(rowSet.getString("tenant_name")).thenReturn(tenant.getTenantName());
        when(rowSet.getString("tenant_phone")).thenReturn(tenant.getTenantPhone());
        when(rowSet.getBigDecimal("monthly_payment")).thenReturn(tenant.getMonthlyPayment());
        when(rowSet.getBigDecimal("current_rent_owed")).thenReturn(tenant.getCurrentRentOwed());
        when(rowSet.getBoolean("has_pet")).thenReturn(tenant.isHasPet());
        when(rowSet.getDate("move_in_date")).thenReturn(Date.valueOf(tenant.getMoveInDate()));

        // When
        Tenant mappedTenant = jdbcTenantDao.mapRowToTenant(rowSet);
        assertEquals(tenant, mappedTenant);
    }

    }


