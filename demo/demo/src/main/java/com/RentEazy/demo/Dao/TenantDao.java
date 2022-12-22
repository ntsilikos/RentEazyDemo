package com.RentEazy.demo.Dao;

import com.RentEazy.demo.Model.Tenant;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;

public interface TenantDao {
    boolean create(Tenant tenant);
    ArrayList<Tenant> listTenants();
    Tenant mapRowToTenant(SqlRowSet rowSet);
}
