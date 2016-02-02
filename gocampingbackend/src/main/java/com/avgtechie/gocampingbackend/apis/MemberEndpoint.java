package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.objectifymodels.Member;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpMethods;
import com.googlecode.objectify.Work;

import java.util.List;
import java.util.logging.Logger;

import static com.avgtechie.gocampingbackend.OfyService.ofy;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class MemberEndpoint {

    private static final Logger LOG = Logger.getLogger(MemberEndpoint.class.getName());

    @ApiMethod(httpMethod = HttpMethods.POST,name = "getAllFamilyMembers")
    public List<Member> getAllFamilyMembers(@Named("familyId") Long familyId) throws NotFoundException {
        return DatastoreUtility.findSavedMembersByFamilyId(familyId);
    }

    @ApiMethod(httpMethod = HttpMethods.POST,name = "getMember")
    public Member getMember(@Named("memberId") Long memberId){
        return DatastoreUtility.findSavedMemberById(memberId);
    }

    @ApiMethod(httpMethod = HttpMethods.POST,name = "deleteMember")
    public void deleteMember(@Named("memberId") Long memberId, @Named("familyId") Long familyId, @Named("campingTripId") Long campingTripId) throws NotFoundException {
        // TODO: 2/2/16 find Member and handle validation.
        Member savedMember = DatastoreUtility.findSavedMemberById(memberId);
        if(savedMember == null){
            throw new NotFoundException("unable to find member with provided Id "+memberId);
        }
        // TODO: 2/2/16 find family and remove memeber from family.
        // TODO: 2/2/16 find campingTrip and handle validation.
        // TODO: 2/2/16 decreament count from camping trip 
        // TODO: 2/2/16 update camping all families owed expense.
    }

    @ApiMethod(name = "addMember", httpMethod = ApiMethod.HttpMethod.POST)
    public void addMember(final Member member, @Named("familyId") final Long familyId, @Named("campingTripId") final Long campingTripId) throws IllegalArgumentException, NotFoundException {
        if(member == null){
            throw new IllegalArgumentException("member object can't be null.");
        }

        final Family savedFamily = DatastoreUtility.findSavedFamily(familyId);
        if(savedFamily == null){
            throw new NotFoundException("unable to find Family with provided Id " + familyId);
        }

        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if(savedCampingTrip == null){
            throw new NotFoundException("unable to find CampingTrip with provided Id "+ campingTripId);
        }

        Boolean success = ofy().transact(new Work<Boolean>() {
            @Override
            public Boolean run() {
                ofy().save().entity(member).now();
                // TODO: 2/2/16 add Member to family.
                savedFamily.getMemberIds().add(member.getId());
                savedCampingTrip.increamentTotalMembersCount();
                try {
                    DatastoreUtility.updateAllFamiliesOwedExpenseFromCampingTrip(campingTripId);
                } catch (NotFoundException e) {
                    return false;
                }
                return true;
            }
        });

        if(!success){
            throw new NotFoundException("unable to find CampingTrip with provided Id "+ campingTripId);
        }
    }
}
