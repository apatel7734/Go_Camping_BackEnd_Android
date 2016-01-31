package com.avgtechie.gocampingbackend.objectifymodels;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

/**
 * Created by fob966 on 1/25/16.
 */
@Entity
public class Family {

    @Id
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private double totalSpentExpenseAmount;
    private double totalOwedExpenseAmount;
    private TripRSVPStatus tripRSVPStatus;
    private List<Long> expenseIds;
    private List<Long> memberIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getTotalSpentExpenseAmount() {
        return totalSpentExpenseAmount;
    }

    public void setTotalSpentExpenseAmount(double totalSpentExpenseAmount) {
        this.totalSpentExpenseAmount = totalSpentExpenseAmount;
    }

    public double getTotalOwedExpenseAmount() {
        return totalOwedExpenseAmount;
    }

    public void setTotalOwedExpenseAmount(double totalOwedExpenseAmount) {
        this.totalOwedExpenseAmount = totalOwedExpenseAmount;
    }

    public TripRSVPStatus getTripRSVPStatus() {
        return tripRSVPStatus;
    }

    public void setTripRSVPStatus(TripRSVPStatus tripRSVPStatus) {
        this.tripRSVPStatus = tripRSVPStatus;
    }

    public List<Long> getExpenseIds() {
        return expenseIds;
    }

    public void setExpenseIds(List<Long> expenseIds) {
        this.expenseIds = expenseIds;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
