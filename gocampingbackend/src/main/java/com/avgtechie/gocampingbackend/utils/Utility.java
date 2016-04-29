package com.avgtechie.gocampingbackend.utils;

/**
 * Created by fob966 on 1/26/16.
 */
public class Utility {

    /**
     *
     * @param phoneNumber - 10 digit valid US phoneNumber
     * @return NULL if length of phone number is not null
     * @return phoneNumber in format xxx-xxx-xxxx
     */
    public static String formatPhoneNumber(String phoneNumber){
        if(phoneNumber.length() != 10){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for (Character c : phoneNumber.toCharArray()) {
            System.out.println("Character = " + c);
            if(counter == 3 || counter == 6){
                stringBuilder.append("-");
            }
            stringBuilder.append(c);
            counter++;
        }

        return  stringBuilder.toString();
    }

}
