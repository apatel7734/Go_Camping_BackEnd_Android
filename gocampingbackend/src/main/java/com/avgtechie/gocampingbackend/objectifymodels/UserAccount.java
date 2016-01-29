package com.avgtechie.gocampingbackend.objectifymodels;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

/**
 * Created by fob966 on 1/28/16.
 */

@Entity
public class UserAccount {
    @Id
    long phoneNumber;
    String fullName;
    String email;
    List<String> campingTripsKeys;
    List<String> membersKeys;

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCampingTripsKeys() {
        return campingTripsKeys;
    }

    public void setCampingTripsKeys(List<String> campingTripsKeys) {
        this.campingTripsKeys = campingTripsKeys;
    }

    public List<String> getMembersKeys() {
        return membersKeys;
    }

    public void setMembersKeys(List<String> membersKeys) {
        this.membersKeys = membersKeys;
    }
}
