package com.avgtechie.gocampingbackend.objectifymodels;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

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
    private List<String> familiesKeys;

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

    public List<String> getFamiliesKeys() {
        return familiesKeys;
    }

    public void setFamiliesKeys(List<String> familiesKeys) {
        this.familiesKeys = familiesKeys;
    }
}
