package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.Family;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class FamilyEndpoint {

    @ApiMethod(name = "getFamilies")
    public List<Family> getFamilies(@Named("campingTripId") Long campingTripId){
        List<Family> families = new ArrayList<Family>();
        for(int i=0;i < 100;i++){
            Family family = new Family();
            family.setName("family - "+ i);
            families.add(family);
        }
        return families;
    }

    @ApiMethod(name = "getFamily")
    public Family getFamily(@Named("familyID") Long familyID){
        return null;
    }

    @ApiMethod(name = "addFamily")
    public void addFamily(Family family){
        //

    }

    @ApiMethod(name = "deleteFamily")
    public void deleteFamily(@Named("familyId") Long familyId){

    }


}
