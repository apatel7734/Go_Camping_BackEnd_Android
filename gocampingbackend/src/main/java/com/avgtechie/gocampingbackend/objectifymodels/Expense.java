package com.avgtechie.gocampingbackend.objectifymodels;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by fob966 on 1/25/16.
 */
@Entity
public class Expense {

    @Id
    private Long id;
    private String itemName;
    private String description;
    private double itemCost;
    private Long familyId;

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public boolean validate() throws IllegalArgumentException{
       if(itemName == null || itemName.isEmpty()){
            throw new IllegalArgumentException("item name can't be null or empty.");
       }else if(itemCost <= 0.0){
           throw new IllegalArgumentException("item cost must be greater than zero.");
       }else if(familyId == null){
           throw new IllegalArgumentException("familyId can't be null.");
       }
        return  true;
    }
}
