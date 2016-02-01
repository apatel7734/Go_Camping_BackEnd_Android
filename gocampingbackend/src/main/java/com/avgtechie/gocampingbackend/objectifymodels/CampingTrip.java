package com.avgtechie.gocampingbackend.objectifymodels;

import com.avgtechie.gocampingbackend.utils.CampingTripValidationResult;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fob966 on 1/24/16.
 */
@Entity
public class CampingTrip {

    @Id
    private Long id;
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private String addressString;
    private GeoPt locationPoint;
    private List<Long> familiesIds;
    private int totalMembersComingToTrip;
    private double totalTripExpense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public GeoPt getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(GeoPt locationPoint) {
        this.locationPoint = locationPoint;
    }

    public List<Long> getFamiliesIds() {
        return familiesIds;
    }

    public void setFamiliesIds(List<Long> familiesIds) {
        this.familiesIds = familiesIds;
    }

    public int getTotalMembersComingToTrip() {
        return totalMembersComingToTrip;
    }

    public void setTotalMembersComingToTrip(int totalMembersComingToTrip) {
        this.totalMembersComingToTrip = totalMembersComingToTrip;
    }

    public double getTotalTripExpense() {
        return totalTripExpense;
    }

    public void setTotalTripExpense(double totalTripExpense) {
        this.totalTripExpense = totalTripExpense;
    }

    public void increamentTotalMembersCount(){
        totalMembersComingToTrip++;
    }

    public void decreamentTotalMembersCount(){
        if(totalMembersComingToTrip > 0){
            totalMembersComingToTrip--;
        }
    }

    public CampingTripValidationResult validate(){

        Date today = Calendar.getInstance().getTime();

        // TODO: 1/29/16 remove these test lines.
        //test lines start******
        dateFrom = today;
        dateTo = today;
        //test lines end*******

        if (title == null || title.isEmpty()){
            return new CampingTripValidationResult(false,"Must provide campingTrip Title.");
            // TODO: 1/29/16 remove test lines, just for development.
//        }else if (dateFrom == null || dateFrom.before(today)){
//            return new CampingTripValidationResult(false,"FromDate must be after today's date.");
//        }else if (dateTo == null || dateTo.before(dateFrom)){
//            return new CampingTripValidationResult(false, "ToDate must be after FromDate.");
        }else if (locationPoint == null){
            return new CampingTripValidationResult(false, "Must provide locationPoint");
        }else if (addressString == null || addressString.isEmpty()){
            return new CampingTripValidationResult(false,"Must provide camping address.It can't be empty");
        }else{
            return CampingTripValidationResult.getValidCampingTripResult();
        }
    }
}

