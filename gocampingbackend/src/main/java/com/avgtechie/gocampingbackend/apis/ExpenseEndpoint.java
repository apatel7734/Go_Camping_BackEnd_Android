package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Expense;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpMethods;
import com.google.appengine.repackaged.com.google.api.client.util.store.DataStore;
import com.google.appengine.repackaged.com.google.api.client.util.store.DataStoreUtils;
import com.googlecode.objectify.Work;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class ExpenseEndpoint {


    @ApiMethod(name = "addExpense", httpMethod = HttpMethods.POST)
    public void addExpense(final Expense expense, @Named("campingTripId") final Long campingTripId) throws IllegalArgumentException, NotFoundException {
        try{
            expense.validate();
        }catch (IllegalArgumentException exception){
            throw exception;
        }
        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if(savedCampingTrip == null){
            throw new NotFoundException("could not find CampingTrip with provided campingTripId - "+campingTripId);
        }
        final Family savedFamily = DatastoreUtility.findSavedFamily(expense.getFamilyId());
        if(savedFamily == null){
            throw new NotFoundException("could not find family with provided familyId - "+expense.getFamilyId());
        }
        ofy().transact(new Work<CampingTrip>() {
            @Override
            public CampingTrip run() {
                double newTotalTripExpense = savedCampingTrip.getTotalTripExpense() + expense.getItemCost();
                double newFamilyTotalSpendExpense = savedFamily.getTotalSpentExpenseAmount() + expense.getItemCost();
                savedCampingTrip.setTotalTripExpense(newTotalTripExpense);
                savedFamily.setTotalSpentExpenseAmount(newFamilyTotalSpendExpense);
                ofy().save().entities(expense,savedCampingTrip,savedFamily).now();
                try {
                    DatastoreUtility.updateAllFamiliesOwedExpenseFromCampingTrip(campingTripId);
                } catch (NotFoundException e) {
                    return  null;
                }
                return savedCampingTrip;
            }
        });

        if(savedCampingTrip == null){
            throw new NotFoundException("CampingTrip not available with provided ID = " + savedCampingTrip);
        }
    }


    @ApiMethod(name = "getExpenses")
    public List<Expense> getExpenses(@Named("familyID") Long familyID) throws NotFoundException {
        return DatastoreUtility.findSavedExpensesByFamilyId(familyID);
    }

    @ApiMethod(name = "deleteExpense")
    public void deleteExpense(@Named("expenseID") Long expenseId, @Named("campingTripId") Long campingTripId) throws NotFoundException {
        Expense expense = DatastoreUtility.findSavedExpenseById(expenseId);
        if(expense == null){
            throw new NotFoundException("unable to find expense by id "+expenseId);
        }
        Family family = DatastoreUtility.findSavedFamily(expense.getFamilyId());
        if(family == null){
            throw new NotFoundException("unable to find family for the expense "+expenseId);
        }
        CampingTrip campingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if(campingTrip == null){
            throw new NotFoundException("unable to find campingTrip by id "+campingTripId);
        }
        double itemCost = expense.getItemCost();
        double newCampingTripTotalExpense = campingTrip.getTotalTripExpense() - itemCost;
        double newFamilyTotalSpentExpense = family.getTotalSpentExpenseAmount() - itemCost;
        campingTrip.setTotalTripExpense(newCampingTripTotalExpense);
        family.setTotalSpentExpenseAmount(newFamilyTotalSpentExpense);



    }

    @ApiMethod(name = "deleteAllExpenses")
    public void deleteAllExpenses(@Named("familyID") Long familyID){

    }

    @ApiMethod(name = "getExpense")
    public Expense getExpense(@Named("expenseID") Long expenseID){

        return null;
    }
}
