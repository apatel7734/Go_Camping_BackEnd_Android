package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.FamiliesWrapper;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.utils.CampingTripValidationResult;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class FamilyEndpoint {

    private static final Logger LOG =
            Logger.getLogger(FamilyEndpoint.class.getName());

    @ApiMethod(name = "inviteFamilies")
    public void inviteFamilies(@Named("campingTripId") Long campingTripId, FamiliesWrapper familiesWrapper) throws  IllegalArgumentException{
        //validate and get families list
        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if(savedCampingTrip == null){
            throw new IllegalArgumentException("Can't find CampingTripID with provided ID " + campingTripId);
        }
        //save families
        final List<Family> invitedFamilies = familiesWrapper.getFamilies();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                Map<Key<Family>, Family> savedFamilies = ofy().save().entities(invitedFamilies).now();
                LOG.info("Saved Families = "+savedFamilies);
                LOG.info("Saved Camping Trip = "+savedCampingTrip);
                List<Long> familyKeys = savedCampingTrip.getFamiliesKeys();
                if(familyKeys == null){
                    familyKeys = new ArrayList<Long>();
                }
                for (Key<Family> familyKey : savedFamilies.keySet()) {
                    familyKeys.add(familyKey.getId());
                }
                savedCampingTrip.setFamiliesKeys(familyKeys);
                ofy().save().entity(savedCampingTrip).now();
            }
        });

        // add back end queue task to send invitation texts to each family
    }

    @ApiMethod(name = "getFamily")
    public Family getFamily(@Named("familyID") Long familyID){

        //load family by familyID
        return null;
    }

    @ApiMethod(name = "deleteFamily")
    public void deleteFamilies(FamiliesWrapper familiesWrapper){

    }



    @ApiMethod(name="rsvpToTheTrip")
    public void rsvpToTheTrip(@Named("familyID") String familyID, @Named("campingTripID") String campingTripID){

    }


}
