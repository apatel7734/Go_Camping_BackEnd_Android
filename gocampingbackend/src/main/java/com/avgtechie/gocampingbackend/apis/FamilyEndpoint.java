package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.FamiliesWrapper;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.objectifymodels.TripRSVPStatus;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.Collection;
import java.util.List;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class FamilyEndpoint {

    @ApiMethod(name = "inviteFamilies")
    public List<Family> inviteFamilies(@Named("campingTripId") Long campingTripId, FamiliesWrapper familiesWrapper){

        //validate and get families list

        //save families

        // add back end queue task to send invitation texts to each family

        return familiesWrapper.getFamilies();
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
