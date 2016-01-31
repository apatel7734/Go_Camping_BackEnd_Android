package com.avgtechie.gocampingbackend.objectifymodels;

/**
 * Created by fob966 on 1/31/16.
 */
public class FamilyRSVPWrapper {

    private Long familyId;
    private int totalMembersComing;
    private Long campingTripId;
    private TripRSVPStatus familyRSVPedResponse;

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public int getTotalMembersComing() {
        return totalMembersComing;
    }

    public void setTotalMembersComing(int totalMembersComing) {
        this.totalMembersComing = totalMembersComing;
    }

    public Long getCampingTripId() {
        return campingTripId;
    }

    public void setCampingTripId(Long campingTripId) {
        this.campingTripId = campingTripId;
    }

    public TripRSVPStatus getFamilyRSVPedResponse() {
        return familyRSVPedResponse;
    }

    public void setFamilyRSVPedResponse(TripRSVPStatus familyRSVPedResponse) {
        this.familyRSVPedResponse = familyRSVPedResponse;
    }
}
