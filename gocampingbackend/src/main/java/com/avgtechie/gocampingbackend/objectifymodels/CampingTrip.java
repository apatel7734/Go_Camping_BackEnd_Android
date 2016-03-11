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
    private Long dateFrom;
    private Long dateTo;
    private String addressString;
    private GeoPt locationPoint;
    private List<Long> familiesIds;
    private int totalMembersComingToTrip;
    private double totalTripExpense;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
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
        Date fromDate = new Date(dateFrom);
        Date toDate = new Date(dateTo);

        if (title == null || title.isEmpty()){
            return new CampingTripValidationResult(false,"Must provide campingTrip Title.");
        }else if (fromDate == null || fromDate.before(today)){
            return new CampingTripValidationResult(false,"FromDate must be after today's date.");
        }else if (toDate == null || toDate.before(fromDate)){
            return new CampingTripValidationResult(false, "ToDate must be after FromDate.");
        }else if (locationPoint == null){
            return new CampingTripValidationResult(false, "Must provide locationPoint");
        }else if (addressString == null || addressString.isEmpty()){
            return new CampingTripValidationResult(false,"Must provide camping address.It can't be empty");
        }else{
            return CampingTripValidationResult.getValidCampingTripResult();
        }
    }
}

