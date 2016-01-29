package com.avgtechie.gocampingbackend.objectifymodels;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Created by fob966 on 1/28/16.
 */
@Entity
public class NewUsersTripInvites {

    @Id
    private Long tripInviteId;
    private long phoneNumber;
    private Key<CampingTrip> campingTripKey;

    public Long getTripInviteId() {
        return tripInviteId;
    }

    public void setTripInviteId(Long tripInviteId) {
        this.tripInviteId = tripInviteId;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Key<CampingTrip> getCampingTripKey() {
        return campingTripKey;
    }

    public void setCampingTripKey(Key<CampingTrip> campingTripKey) {
        this.campingTripKey = campingTripKey;
    }
}
