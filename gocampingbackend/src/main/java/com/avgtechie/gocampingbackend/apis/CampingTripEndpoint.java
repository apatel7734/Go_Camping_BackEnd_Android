package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.utils.CampingTripValidationResult;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.util.logging.Logger;
import java.util.List;
import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip.*;


import static com.avgtechie.gocampingbackend.OfyService.ofy;


/**
 * Created by fob966 on 1/24/16.
 */
@Api(name = "gocamping")
public class CampingTripEndpoint {

    private static final Logger LOG =
            Logger.getLogger(CampingTripEndpoint.class.getName());

    @ApiMethod(httpMethod = "GET",name = "getCampingTrips")
    public List<CampingTrip> getUsersCampingTrips(@Named("userPhoneNumber") long userPhoneNumber){
            //not null phoneNumber

            //get userAccount by phone number and user campingKeys

            //get new invitees users camping Keys

            //load all campingTrips by Keys

            //generate object and return.
        return null;
    }


    @ApiMethod(httpMethod = "POST", name = "createCampingTrip")
    public final CampingTripValidationResult createCampingTrip(final CampingTrip campingTrip){
        CampingTripValidationResult validationResult = campingTrip.validate();
        if(!validationResult.isValid()){
            return validationResult;
        }
        ofy().save().entity(campingTrip);
        return validationResult;
    }

    @ApiMethod(httpMethod = "POST", name = "deleteCampingTrip")
    public void deleteCampingTrip(@Named("campingTripId") Long campingTripId){
        //check camping trip exist
        //delete camping trip by key
        //delete all families Entity from FamilyKind
        //delete all members Entity from MemberKind
        //delete all expense Entity from ExpenseKind
    }
}
