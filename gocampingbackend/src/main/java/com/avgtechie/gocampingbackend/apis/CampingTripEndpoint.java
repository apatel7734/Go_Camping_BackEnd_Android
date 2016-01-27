package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.CampingTrip;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.List;

/**
 * Created by fob966 on 1/24/16.
 */
@Api(name = "gocamping")
public class CampingTripEndpoint {

    @ApiMethod(name = "getCampingTrips")
    public List<CampingTrip> getCampingTrips(){

        return null;
    }

    @ApiMethod(name = "addCampingTrip")
    public void addCampingTrip(CampingTrip campingTrip){

    }

    @ApiMethod(name = "deleteCampingTrip")
    public void deleteCampingTrip(@Named("campingTripId") Long campingTripId){

    }

}
