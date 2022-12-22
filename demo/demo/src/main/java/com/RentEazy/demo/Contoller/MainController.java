package com.RentEazy.demo.Contoller;

import com.RentEazy.demo.Dao.JdbcTenantDao;
import com.RentEazy.demo.Dao.TenantDao;
import com.RentEazy.demo.Model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MainController {

    @Autowired
    JdbcTenantDao tenantDao;

    @RequestMapping(path = "/api/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testControl() {

        return "hello world";
    }

    @PostMapping
    @RequestMapping(path = "/tenants", method = RequestMethod.POST, consumes = "application/json")
    public boolean createTenant(@RequestBody Tenant tenant) {
        return tenantDao.create(tenant);
    }

}
