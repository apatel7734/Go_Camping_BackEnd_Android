package com.avgtechie.gocampingbackend.utils;

/**
 * Created by fob966 on 1/29/16.
 */

public class CampingTripValidationResult{
    private boolean isValid;
    private String errorMessage;

    public CampingTripValidationResult(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    static public CampingTripValidationResult getValidCampingTripResult(){
        return new CampingTripValidationResult(true,"Success");
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
