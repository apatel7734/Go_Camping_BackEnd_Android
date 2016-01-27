package com.avgtechie.gocampingbackend.models;

import java.util.List;

/**
 * Created by fob966 on 1/25/16.
 */
public class Family {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Long campingTripID;
    private List<Long> expenseIds;
    private List<Long> membersIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCampingTripID() {
        return campingTripID;
    }

    public void setCampingTripID(Long campingTripID) {
        this.campingTripID = campingTripID;
    }

    public List<Long> getExpenseIds() {
        return expenseIds;
    }

    public void setExpenseIds(List<Long> expenseIds) {
        this.expenseIds = expenseIds;
    }

    public List<Long> getMembersIds() {
        return membersIds;
    }

    public void setMembersIds(List<Long> membersIds) {
        this.membersIds = membersIds;
    }
}
