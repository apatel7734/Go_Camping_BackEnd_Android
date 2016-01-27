package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.CampingTrip;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.util.List;

import static com.avgtechie.gocampingbackend.OfyService.ofy;


/**
 * Created by fob966 on 1/24/16.
 */
@Api(name = "gocamping")
public class CampingTripEndpoint {

    @ApiMethod(name = "getCampingTrips")
    public List<CampingTrip> getCampingTrips(){

        return null;
    }

    @ApiMethod(httpMethod = "POST")
    public final void addCampingTrip(final CampingTrip campingTrip){
        ofy().save().entity(campingTrip);
    }

    @ApiMethod(name = "deleteCampingTrip")
    public void deleteCampingTrip(@Named("campingTripId") Long campingTripId){

    }

}
