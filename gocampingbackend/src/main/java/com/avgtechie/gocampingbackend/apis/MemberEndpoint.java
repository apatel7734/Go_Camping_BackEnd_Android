package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.models.Member;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import java.util.List;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class MemberEndpoint {

    @ApiMethod(name = "getMembers")
    public List<Member> getMembers(@Named("familyID") Long familyID){
        return null;
    }

    @ApiMethod(name = "getMember")
    public Member getMember(@Named("memberID") Long memberID){
        return  null;
    }

    @ApiMethod(name = "deleteMember")
    public void deleteMember(@Named("memberID") Long memberID){

    }

    @ApiMethod(name = "deleteAllMembers")
    public void deleteAllMembers(@Named("familyID") Long familyID){

    }
}
