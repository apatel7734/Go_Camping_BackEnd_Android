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
    private List<CampingTrip> campingTrips;
    private double totalSpendExpenseAmount;
    private double totalOwedExpenseAmount;
    private List<Expense> expenses;
    private List<Member> members;

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

    public List<CampingTrip> getCampingTrips() {
        return campingTrips;
    }

    public void setCampingTrips(List<CampingTrip> campingTrips) {
        this.campingTrips = campingTrips;
    }

    public double getTotalSpendExpenseAmount() {
        return totalSpendExpenseAmount;
    }

    public void setTotalSpendExpenseAmount(double totalSpendExpenseAmount) {
        this.totalSpendExpenseAmount = totalSpendExpenseAmount;
    }

    public double getTotalOwedExpenseAmount() {
        return totalOwedExpenseAmount;
    }

    public void setTotalOwedExpenseAmount(double totalOwedExpenseAmount) {
        this.totalOwedExpenseAmount = totalOwedExpenseAmount;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
