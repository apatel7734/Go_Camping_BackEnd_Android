package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.Expense;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.List;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class ExpenseEndpoint {

    @ApiMethod(name = "getExpenses")
    public List<Expense> getExpenses(@Named("familyID") Long familyID){

        return null;
    }

    @ApiMethod(name = "deleteExpense")
    public void deleteExpense(@Named("expenseID") Long expenseID){

    }

    @ApiMethod(name = "deleteAllExpenses")
    public void deleteAllExpenses(@Named("familyID") Long familyID){

    }

    @ApiMethod(name = "getExpense")
    public Expense getExpense(@Named("expenseID") Long expenseID){

        return null;
    }
}
