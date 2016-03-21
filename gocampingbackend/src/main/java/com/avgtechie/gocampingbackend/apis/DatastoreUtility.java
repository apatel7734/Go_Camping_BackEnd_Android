package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Expense;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.objectifymodels.Member;
import com.avgtechie.gocampingbackend.objectifymodels.TripRSVPStatus;
import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.avgtechie.gocampingbackend.OfyService.ofy;

/**
 * Created by fob966 on 1/30/16.
 */
public class DatastoreUtility{


    static UserAccount findSavedUserAccount(final long phoneNumber) {
        return ofy().load().key(Key.create(UserAccount.class, phoneNumber)).now();
    }

    static CampingTrip findSavedCampingTrip(final long campingTripId){
        return ofy().load().type(CampingTrip.class).id(campingTripId).now();

    }

    static Family findSavedFamily(final long familyId){
        return ofy().load().type(Family.class).id(familyId).now();
    }

    static List<CampingTrip> findSavedCampingTripsByIds(List<Long> campingTripsIds){
        Map<Long, CampingTrip> savedCampingTrips = ofy().load().type(CampingTrip.class).ids(campingTripsIds);
        return new ArrayList<CampingTrip>(savedCampingTrips.values());
    }

    static List<Family> findSavedFamiliesByIds(List<Long> familyIds){
        Map<Long, Family> savedFamilies = ofy().load().type(Family.class).ids(familyIds);
        return new ArrayList<Family>(savedFamilies.values());
    }

    static List<Family> findSavedFamiliesForCampingTripId(final long campingTripId)  throws NotFoundException{
        CampingTrip savedCampingTrip = findSavedCampingTrip(campingTripId);
        if(savedCampingTrip == null){
            throw new NotFoundException("CampingTrip doesn't exist with provided campingTripId = " + campingTripId);
        }
        List<Long> familyIds = savedCampingTrip.getFamiliesIds();
        return findSavedFamiliesByIds(familyIds);
    }

    static void updateAllFamiliesOwedExpenseFromCampingTrip(Long campingTripId) throws IllegalArgumentException, NotFoundException {

        CampingTrip savedCampingTrip = findSavedCampingTrip(campingTripId);
        if(savedCampingTrip == null){
            throw new IllegalArgumentException("Null campingTrip not allowed.");
        }

        List<Family> families = findSavedFamiliesForCampingTripId(savedCampingTrip.getId());

        if(families == null || families.size() <= 0){
            throw new NotFoundException("No family found for the CampingTrip : "+campingTripId);
        }

        double totalExpensePerMember = savedCampingTrip.getTotalTripExpense() / savedCampingTrip.getTotalMembersComingToTrip();


        for (Family family : families) {
            if(family.getMemberIds() == null || family.getMemberIds().size() <= 0){
                continue;
            }
            double totalFamilyOwedExpense = family.getMemberIds().size() * totalExpensePerMember;
            family.setTotalOwedExpenseAmount(totalFamilyOwedExpense);
        }
        ofy().save().entities(families).now();
    }

    static List<Expense> findSavedExpensesByIds(List<Long> expenseIds){
        if(expenseIds == null || expenseIds.size() <= 0){
            new ArrayList<Expense>();
        }
        Map<Long, Expense> savedExpenses = ofy().load().type(Expense.class).ids(expenseIds);
        if(savedExpenses == null){
            return new ArrayList<Expense>();
        }
        return new ArrayList<Expense>(savedExpenses.values());
    }

    static List<Member> findSavedMembersByIds(List<Long> membersIds){
        if(membersIds == null || membersIds.size() <= 0){
            return new ArrayList<Member>();
        }
        Map<Long, Member> savedMembers = ofy().load().type(Member.class).ids(membersIds);
        if(savedMembers == null){
            return new ArrayList<Member>();
        }
        return new ArrayList<Member>(savedMembers.values());
    }

    static Expense findSavedExpenseById(Long expenseId){
        return ofy().load().type(Expense.class).id(expenseId).now();
    }

    static Member findSavedMemberById(Long memberId){
        return ofy().load().type(Member.class).id(memberId).now();
    }

    static  void deleteExpenseById(Long expenseId) throws NotFoundException {
        Expense expense = findSavedExpenseById(expenseId);
        if(expense == null){
            throw new NotFoundException("Unable to find expense by provided id " + expenseId);
        }
        ofy().delete().entity(expense).now();
    }

    static List<Expense> findSavedExpensesByFamilyId(Long familyId) throws NotFoundException{
        Family family = findSavedFamily(familyId);
        if (family == null){
            throw new NotFoundException("Family not found with provided id = " + familyId);
        }
        return findSavedExpensesByIds(family.getExpenseIds());
    }

    static List<Member> findSavedMembersByFamilyId(Long familyId) throws NotFoundException {
        Family family = findSavedFamily(familyId);
        if(family == null){
            throw new NotFoundException("Family not found with provided id = " + familyId);
        }

        return  findSavedMembersByIds(family.getMemberIds());
    }

    static Key<Family> saveFamilyForUser(UserAccount userAccount){

        if(userAccount != null){
            Family family = new Family();
            family.setFullName(userAccount.getFullName());
            family.setEmail(userAccount.getEmail());
            family.setPhoneNumber(String.valueOf(userAccount.getPhoneNumber()));
            family.setTripRSVPStatus(TripRSVPStatus.YES);
            return ofy().save().entity(family).now();
        }
        return null;
    }
}
