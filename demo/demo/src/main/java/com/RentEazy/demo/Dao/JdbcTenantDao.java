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

    @Override
    public boolean create(Tenant tenant) {
        String sql = "INSERT INTO tenants(tenant_name, tenant_phone, monthly_payment, current_rent_owed, has_pet, move_in_date, security_deposit, current_month_paid)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tenant.getTenantName(), tenant.getTenantPhone(), tenant.getMonthlyPayment(), tenant.getCurrentRentOwed(),
                tenant.isHasPet(), tenant.getMoveInDate(), tenant.getSecurityDeposit(), tenant.isCurrentMonthPaid()) == 1;
    }

    @Override
    public boolean delete(String name) {
        String sql = "DELETE FROM tenants WHERE tenant_name = ?";
        String sqlTransactionDelete = "DELETE FROM transaction_history WHERE tenant_id = (SELECT tenant_id FROM tenants WHERE tenant_name = ?)";
        jdbcTemplate.update(sqlTransactionDelete, name);
        return jdbcTemplate.update(sql, name) == 1;
    }

    @Override
    public boolean edit(Tenant tenant) {
        String sql = "UPDATE tenants SET tenant_phone = ?, monthly_payment = ?, current_rent_owed = ?, has_pet = ?, move_in_date = ?, security_deposit = ?, current_month_paid = ? WHERE tenant_name = ?";
        return jdbcTemplate.update(sql, tenant.getTenantPhone(), tenant.getMonthlyPayment(), tenant.getCurrentRentOwed(), tenant.isHasPet(), tenant.getMoveInDate(), tenant.getSecurityDeposit(), tenant.isCurrentMonthPaid(), tenant.getTenantName()) == 1;
    }

    @Override
    public ArrayList<Tenant> listTenants() {
        ArrayList<Tenant> tenants = new ArrayList<>();

        String sql = "SELECT units.*, tenants.*, buildings.* " +
                "FROM units " +
                "JOIN tenant_unit ON units.unit_id = tenant_unit.unit_id " +
                "JOIN tenants ON tenant_unit.tenant_id = tenants.tenant_id " +
                "JOIN building_unit ON units.unit_id = building_unit.unit_id " +
                "JOIN buildings ON building_unit.building_id = buildings.id";

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
        String sql = "SELECT * FROM tenants WHERE tenant_name = ?";
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
        tenant.setTenantPhone(rowSet.getString("tenant_phone"));
        tenant.setMonthlyPayment(rowSet.getBigDecimal("monthly_payment"));
        tenant.setCurrentRentOwed(rowSet.getBigDecimal("current_rent_owed"));
        tenant.setSecurityDeposit(rowSet.getBigDecimal("security_deposit"));
        tenant.setHasPet(rowSet.getBoolean("has_pet"));
        tenant.setCurrentMonthPaid(rowSet.getBoolean("current_month_paid"));
        tenant.setMoveInDate(rowSet.getDate("move_in_date").toLocalDate());
        tenant.setBuilding(rowSet.getString("address"));
        tenant.setUnitNumber(rowSet.getString("unit_number"));
        return tenant;
    }


}
