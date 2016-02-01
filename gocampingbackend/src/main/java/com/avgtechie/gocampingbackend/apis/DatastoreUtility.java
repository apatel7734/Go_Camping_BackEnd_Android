package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
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
}
