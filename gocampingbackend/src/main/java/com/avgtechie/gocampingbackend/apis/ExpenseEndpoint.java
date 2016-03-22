package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Expense;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpMethods;
import com.google.appengine.repackaged.com.google.common.base.Flag;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class ExpenseEndpoint {

    private static final Logger LOG = Logger.getLogger(ExpenseEndpoint.class.getName());

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
                List<Long> expenseIds = savedFamily.getExpenseIds();
                if(expenseIds == null){
                    expenseIds = new ArrayList<Long>();
                }
                Key<Expense> savedExpense = ofy().save().entity(expense).now();
                expenseIds.add(savedExpense.getId());
                savedFamily.setExpenseIds(expenseIds);
                ofy().save().entities(savedCampingTrip,savedFamily).now();
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

    @ApiMethod(httpMethod = HttpMethods.POST,name = "updateExpense")
    public void updateExpense(final Expense expense) throws NullPointerException, NotFoundException {
        if(expense == null){
            throw new NullPointerException("Expense object can't be null to update.");
        }else if(expense != null){
            Expense savedExpense = DatastoreUtility.findSavedExpenseById(expense.getId());
            if(savedExpense == null){
                throw new NotFoundException("Unable to find expense to update.");
            }else{
                ofy().save().entities(expense);
            }
        }
    }


    @ApiMethod(httpMethod = HttpMethods.POST,name = "getAllFamilyExpenses")
    public List<Expense> getAllFamilyExpenses(@Named("familyID") Long familyID) throws NotFoundException {
        return DatastoreUtility.findSavedExpensesByFamilyId(familyID);
    }

    @ApiMethod(httpMethod = HttpMethods.POST,name = "deleteExpense")
    public void deleteExpense(@Named("expenseID") Long expenseId, @Named("campingTripId") final Long campingTripId) throws NotFoundException {
        final Expense expense = DatastoreUtility.findSavedExpenseById(expenseId);
        if(expense == null){
            throw new NotFoundException("unable to find expense by id "+expenseId);
        }
        final Family family = DatastoreUtility.findSavedFamily(expense.getFamilyId());
        if(family == null){
            throw new NotFoundException("unable to find family for the expense "+expenseId);
        }
        final CampingTrip campingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if(campingTrip == null){
            throw new NotFoundException("unable to find campingTrip by id "+campingTripId);
        }
        double itemCost = expense.getItemCost();
        final double newCampingTripTotalExpense = campingTrip.getTotalTripExpense() - itemCost;
        final double newFamilyTotalSpentExpense = family.getTotalSpentExpenseAmount() - itemCost;
        if(newCampingTripTotalExpense > 0.0 && newFamilyTotalSpentExpense > 0.0){
            campingTrip.setTotalTripExpense(newCampingTripTotalExpense);
            family.setTotalSpentExpenseAmount(newFamilyTotalSpentExpense);
            family.getExpenseIds().remove(expense.getId());
           Boolean success = ofy().transact(new Work<Boolean>() {
               @Override
               public Boolean run() {
                   ofy().save().entities(campingTrip, family);
                   ofy().delete().entity(expense);
                   try {
                       DatastoreUtility.updateAllFamiliesOwedExpenseFromCampingTrip(campingTripId);
                   } catch (NotFoundException e) {
                       return false;
                   }
                   return true;
               }
           });

            if(!success){
                throw new NotFoundException("Error finding campingTrip by provided Id " + campingTripId);
            }
        }
    }
}
