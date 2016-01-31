package com.avgtechie.gocampingbackend.objectifymodels;

import java.util.List;

/**
 * Created by fob966 on 1/30/16.
 */
public class CampingTripWrapper {
    private List<CampingTrip> campingTrips;
    private CampingTrip campingTrip;
    private UserAccount userAccount;

    public List<CampingTrip> getCampingTrips() {
        return campingTrips;
    }

    public void setCampingTrips(List<CampingTrip> campingTrips) {
        this.campingTrips = campingTrips;
    }

    public CampingTrip getCampingTrip() {
        return campingTrip;
    }

    public void setCampingTrip(CampingTrip campingTrip) {
        this.campingTrip = campingTrip;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
