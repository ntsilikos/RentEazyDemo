package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Tenant;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;

public interface TenantDao {

    boolean create(Tenant tenant);
    boolean delete(String name);
    boolean edit(Tenant tenant);

    ArrayList<Tenant> listTenants();
    ArrayList<Tenant> getTenantByName(String name);
    Tenant mapRowToTenant(SqlRowSet rowSet);
}
