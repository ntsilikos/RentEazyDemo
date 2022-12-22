package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Component
public class JdbcTenantDao implements TenantDao{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public JdbcTenantDao() {
        jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public boolean create(Tenant tenant) {
        String sql = "INSERT INTO tenants(tenant_name, tenant_phone, monthly_payment, current_rent_owed, has_pet, move_in_date)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tenant.getTenantName(), tenant.getTenantPhone(), tenant.getMonthlyPayment(), tenant.getCurrentRentOwed(),
                tenant.isHasPet(), tenant.getMoveInDate()) == 1;
    }

    @Override
    public ArrayList<Tenant> listTenants() {
        ArrayList<Tenant> tenants = new ArrayList<>();

        String sql = "SELECT tenant_name FROM tenants";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            //			CatCard card = mapRowToCard(results);
            Tenant tenant = mapRowToTenant(results);
            tenants.add(tenant);

        }
        return tenants;
    }

    @Override
    public ArrayList<Tenant> getTenantByName(String name) {

        ArrayList<Tenant> tenants = new ArrayList<>();
        String sql = "SELECT tenant_name FROM tenants WHERE tenant_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        // implement the method to retrieve a tenant by name
        while(results.next()) {
            Tenant tenant = mapRowToTenant(results);
            tenants.add(tenant);
        }
        return tenants;
    }

    @Override
    public Tenant mapRowToTenant(SqlRowSet rowSet) {
        Tenant tenant = new Tenant();
        tenant.setTenantName(rowSet.getString("tenant_name"));
        return tenant;
    }


}
