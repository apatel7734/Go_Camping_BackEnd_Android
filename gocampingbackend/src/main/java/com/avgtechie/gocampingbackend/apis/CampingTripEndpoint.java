package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.CampingTrip;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;

import java.util.List;

/**
 * Created by fob966 on 1/24/16.
 */
@Api(name = "gocamping")
public class CampingTripEndpoint {

    public List<CampingTrip> getCampingTrips(){

        return null;
    }

    public void addCampingTrip(CampingTrip campingTrip){

    }

    public void deleteCampingTrip(@Named("campingTripId") Long campingTripId){

    }

}
