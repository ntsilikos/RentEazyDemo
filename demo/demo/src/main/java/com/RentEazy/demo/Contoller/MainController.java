package com.RentEazy.demo.Contoller;

import com.RentEazy.demo.Dao.JdbcTenantDao;
import com.RentEazy.demo.Dao.JdbcTransactionDao;
import com.RentEazy.demo.Dao.TenantDao;
import com.RentEazy.demo.Model.Tenant;
import com.RentEazy.demo.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class MainController {

    @Autowired
    JdbcTenantDao tenantDao;
    @Autowired
    JdbcTransactionDao transactionDao;

    @RequestMapping(path = "/api/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testControl() {

        return "hello world";
    }

    @DeleteMapping("/tenants/{name}")
    @ResponseBody
    public boolean deleteTenant(@PathVariable String name) {
        return tenantDao.delete(name);
    }



    @GetMapping("/tenants")
    public ArrayList<Tenant> listTenants() {
        try {
            return tenantDao.listTenants();
        } catch (Exception e) {
            // log the exception
            // return an empty list or a default list
            return new ArrayList<Tenant>();
        }
    }

    @GetMapping("/tenants/{name}")
    public ArrayList<Tenant> getTenantByName(@PathVariable String name) {
        return tenantDao.getTenantByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @RequestMapping(path = "/tenants", method = RequestMethod.POST, consumes = "application/json")
    public boolean createTenant(@RequestBody Tenant tenant) {
        try {
            return tenantDao.create(tenant);
        } catch (Exception e) {
            // log the exception
            // return a default value
            return false;
        }
    }

    @PutMapping
    @RequestMapping(path = "/tenants/{name}", method = RequestMethod.PUT, consumes = "application/json")
    public boolean editTenant(@RequestBody Tenant tenant, @PathVariable String name) {
        try {
            return tenantDao.edit(tenant);
        } catch (Exception e) {
            // log the exception
            // return a default value
            return false;
        }
    }

    @GetMapping("/transactions/{name}")
    public ArrayList<Transaction> getTransactionByName(@PathVariable String name) {
        return transactionDao.getTransactionsByName(name);
    }

    @GetMapping("/transactions")
    public ArrayList<Transaction> listTransactions() {
        try {
            return transactionDao.listTransactions();
        } catch (Exception e) {
            // log the exception
            // return an empty list or a default list
            return new ArrayList<Transaction>();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @RequestMapping(path = "/transactions/{name}", method = RequestMethod.POST, consumes = "application/json")
    public boolean createTransaction(@RequestBody Transaction transaction, @PathVariable String name) {
        return transactionDao.create(transaction, name);
    }

    @DeleteMapping("/transactions/{transactionId}")
    @ResponseBody
    public boolean deleteTransaction(@PathVariable int transactionId) {
        return transactionDao.delete(transactionId);
    }

}
