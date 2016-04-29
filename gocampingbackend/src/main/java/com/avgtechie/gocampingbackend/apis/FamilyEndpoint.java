package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.FamiliesWrapper;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.objectifymodels.FamilyRSVPWrapper;
import com.avgtechie.gocampingbackend.objectifymodels.Member;
import com.avgtechie.gocampingbackend.objectifymodels.TripRSVPStatus;
import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpMethods;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by fob966 on 1/26/16.
 */
@Api(name = "gocamping")
public class FamilyEndpoint {

    private static final Logger LOG = Logger.getLogger(FamilyEndpoint.class.getName());

    @ApiMethod(httpMethod = HttpMethods.GET)
    public void sendMessageTest() {

    }

    @ApiMethod(httpMethod = HttpMethods.POST, name = "getUserFamilyByCampingTrip")
    public Family getUserFamilyByCampingTrip(@Named("campingTripId") Long campingTripId, UserAccount userAccount) throws NotFoundException, UnauthorizedException{
        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);

        UserAccount savedUserAccount = DatastoreUtility.findSavedUserAccount(userAccount.getPhoneNumber());
        //get userAccount by phone number and user campingKeys
        if (savedUserAccount == null) {
            throw new UnauthorizedException("Authorization required.");
        }

        if (savedCampingTrip == null) {
            throw new NotFoundException("Can't find CampingTrip with provided ID " + campingTripId);
        }

        List<Family> families = DatastoreUtility.findSavedFamiliesForCampingTripId(savedCampingTrip.getId());
        Family usersFamily = null;
        for (Family family : families){
            String userPhoneNumber = Long.toString(userAccount.getPhoneNumber());
            if (family.getPhoneNumber().equalsIgnoreCase(userPhoneNumber)){
                usersFamily = family;
                break;
            }
        }

        return usersFamily;
    }

    @ApiMethod(httpMethod = HttpMethods.POST, name = "inviteFamilies")
    public void inviteFamilies(@Named("campingTripId") Long campingTripId, FamiliesWrapper familiesWrapper) throws NotFoundException {

        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(campingTripId);
        if (savedCampingTrip == null) {
            throw new NotFoundException("Can't find CampingTrip with provided ID " + campingTripId);
        }

        final List<Family> invitedFamilies = familiesWrapper.getFamilies();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                Map<Key<Family>, Family> savedFamilies = ofy().save().entities(invitedFamilies).now();
                List<Long> familyKeys = savedCampingTrip.getFamiliesIds();
                if (familyKeys == null) {
                    familyKeys = new ArrayList<Long>();
                }
                for (Key<Family> familyKey : savedFamilies.keySet()) {
                    familyKeys.add(familyKey.getId());
                }
                savedCampingTrip.setFamiliesIds(familyKeys);
                ofy().save().entity(savedCampingTrip).now();
            }
        });

        // TODO: 1/31/16 add task queue to send messages.
    }

    @ApiMethod(httpMethod = HttpMethods.POST, name = "getFamily", path = "familybyid")
    public Family getFamily(@Named("familyID") Long familyId) throws NotFoundException {

        Family savedFamily = DatastoreUtility.findSavedFamily(familyId);
        if (savedFamily == null) {
            throw new NotFoundException("Family is not available by provided ID " + familyId);
        }
        return savedFamily;
    }

    @ApiMethod(httpMethod = HttpMethods.POST, name = "getFamiliesForCampingTrip")
    public List<Family> getFamiliesForCampingTrip(@Named("campingTripId") Long campingTripId) throws NotFoundException {
        return DatastoreUtility.findSavedFamiliesForCampingTripId(campingTripId);
    }

    @ApiMethod(name = "deleteFamily")
    public void deleteFamily(@Named("familyId") Long familyId) {
        // TODO: 1/31/16 find family and delete.
        final Family savedFamily = DatastoreUtility.findSavedFamily(familyId);
        // TODO: 3/11/16 find Expenses and Members for the family and delete
        // TODO: 1/31/16 recalculate campingTrip expense for each Family if RSVPed status = YES.
    }


    @ApiMethod(httpMethod = HttpMethods.POST, name = "rsvpForTheFamily")
    public void rsvpForTheFamily(final FamilyRSVPWrapper familyRSVPWrapper) throws NotFoundException, IllegalArgumentException {
        final Family savedFamily = DatastoreUtility.findSavedFamily(familyRSVPWrapper.getFamilyId());

        if (savedFamily == null) {
            throw new NotFoundException("Unable to find Family with Id : " + familyRSVPWrapper.getFamilyId());
        }

        if (familyRSVPWrapper.getFamilyRSVPedResponse() == TripRSVPStatus.YES && familyRSVPWrapper.getTotalMembersComing() <= 0) {
            throw new IllegalArgumentException("Invalid totalMembersComing for familyId" + familyRSVPWrapper.getFamilyId() + ", When RSVP YES. totalMembersComing must be greater than zero.");
        }

        final CampingTrip savedCampingTrip = DatastoreUtility.findSavedCampingTrip(familyRSVPWrapper.getCampingTripId());
        if (savedCampingTrip == null) {
            throw new NotFoundException("Unable to find CampingTrip with provided Id : " + familyRSVPWrapper.getCampingTripId());
        }

        savedFamily.setTripRSVPStatus(familyRSVPWrapper.getFamilyRSVPedResponse());


        final List<Member> members = new ArrayList<Member>();
        for (int index = 0; index < familyRSVPWrapper.getTotalMembersComing(); index++) {
            Member member = new Member();
            member.setFamilyId(savedFamily.getId());
            member.setName("Member - " + index);
            members.add(member);
            savedCampingTrip.increamentTotalMembersCount();
        }

        Family updatedFamily = ofy().transact(new Work<Family>() {
            @Override
            public Family run() {
                Map<Key<Member>, Member> savedMembers = ofy().save().entities(members).now();
                List<Long> membersIds = savedFamily.getMemberIds();
                if (membersIds == null) {
                    membersIds = new ArrayList<Long>();
                }

                for (Key<Member> memberKey : savedMembers.keySet()) {
                    membersIds.add(memberKey.getId());
                }
                savedFamily.setMemberIds(membersIds);
                ofy().save().entities(savedCampingTrip, savedFamily).now();
                try {
                    DatastoreUtility.updateAllFamiliesOwedExpenseFromCampingTrip(savedCampingTrip.getId());
                } catch (NotFoundException e) {
                    return null;
                }
                return savedFamily;
            }
        });

        if (updatedFamily == null) {
            new NotFoundException("Error finding campingTrip with provided campingTripID = " + savedCampingTrip.getId());
        }
    }


}
