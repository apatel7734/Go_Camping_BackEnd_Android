package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.CampingTripWrapper;
import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.avgtechie.gocampingbackend.utils.CampingTripValidationResult;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpMethods;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.avgtechie.gocampingbackend.OfyService.ofy;


/**
 * Created by fob966 on 1/24/16.
 */
@Api(name = "gocamping")
public class CampingTripEndpoint {

    private static final Logger LOG =
            Logger.getLogger(CampingTripEndpoint.class.getName());

    @ApiMethod(httpMethod = HttpMethods.POST, name = "getCampingTrips")
    public List<CampingTrip> getCampingTrips(UserAccount userAccount) throws UnauthorizedException, NotFoundException {
        //not null phoneNumber
        UserAccount savedUserAccount = DatastoreUtility.findSavedUserAccount(userAccount.getPhoneNumber());
        //get userAccount by phone number and user campingKeys
        if (savedUserAccount == null) {
            throw new UnauthorizedException("Authorization required.");
        }
        //load all campingTrips by Keys
        List<Long> campingTripsIds = savedUserAccount.getCampingTripsKeys();
        if(campingTripsIds == null || campingTripsIds.size() <= 0){
            throw  new NotFoundException("Can't find any campingTrip for currently logged in user.");
        }
        return DatastoreUtility.findSavedCampingTripsByIds(campingTripsIds);
    }


    @ApiMethod(httpMethod = HttpMethods.POST, name = "createCampingTrip")
    public final CampingTripValidationResult createCampingTrip(final CampingTripWrapper campingTripWrapper) throws UnauthorizedException {
        final CampingTrip campingTrip = campingTripWrapper.getCampingTrip();
        final UserAccount userAccount = DatastoreUtility.findSavedUserAccount(campingTripWrapper.getUserAccount().getPhoneNumber());
        if (userAccount == null) {
            return new CampingTripValidationResult(false, "Unauthorized request.");
        }
        CampingTripValidationResult validationResult = campingTrip.validate();

        if (!validationResult.isValid()) {
            return validationResult;
        }

        CampingTrip savedCampingTrip = ofy().transact(new Work<CampingTrip>() {
            @Override
            public CampingTrip run() {
                Key<CampingTrip> savedCampingTrip = ofy().save().entity(campingTripWrapper.getCampingTrip()).now();
                List<Long> userTripKeys = userAccount.getCampingTripsKeys();

                if (userTripKeys == null) {
                    userTripKeys = new ArrayList<Long>();
                }

                userTripKeys.add(savedCampingTrip.getId());
                userAccount.setCampingTripsKeys(userTripKeys);
                ofy().save().entities(campingTripWrapper.getCampingTrip(), userAccount);
                return campingTripWrapper.getCampingTrip();
            }
        });

        if (savedCampingTrip == null) {
            return new CampingTripValidationResult(false, "Error creating CampingTrip.");

        }
        return validationResult;
    }

    @ApiMethod(httpMethod = HttpMethods.POST, name = "deleteCampingTrip")
    public void deleteCampingTrip(@Named("campingTripId") Long campingTripId) {
        //check camping trip exist
        //delete camping trip by key
        //delete all families Entity from FamilyKind
        //delete all members Entity from MemberKind
        //delete all expense Entity from ExpenseKind
    }


}
