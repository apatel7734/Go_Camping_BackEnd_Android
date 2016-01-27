package com.avgtechie.gocampingbackend.models;

/**
 * Created by fob966 on 1/25/16.
 */
public class Expense {

    private Long id;
    private String title;
    private String description;
    private double amount;
    private Long familyId;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
