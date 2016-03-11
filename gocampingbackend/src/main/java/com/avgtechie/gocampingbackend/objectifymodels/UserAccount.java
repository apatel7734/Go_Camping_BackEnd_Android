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
    String password;
    String fullName;
    String email;
    List<Long> campingTripsKeys;
    List<Long> membersKeys;

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

    public List<Long> getCampingTripsKeys() {
        return campingTripsKeys;
    }

    public void setCampingTripsKeys(List<Long> campingTripsKeys) {
        this.campingTripsKeys = campingTripsKeys;
    }

    public List<Long> getMembersKeys() {
        return membersKeys;
    }

    public void setMembersKeys(List<Long> membersKeys) {
        this.membersKeys = membersKeys;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
