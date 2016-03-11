package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.log.InvalidRequestException;

import java.util.ArrayList;
import java.util.logging.Logger;

import static com.avgtechie.gocampingbackend.OfyService.ofy;


/**
 * Created by fob966 on 1/28/16.
 */
@Api(name = "gocamping")
public class UserAccountEndpoint {

    private static final Logger LOG = Logger.getLogger(UserAccountEndpoint.class.getName());

    @ApiMethod(name = "registerUser")
    public void registerUser(UserAccount userAccount) throws InvalidRequestException{
        //validate fields not null
        if(userAccount == null){
            LOG.warning("invalid request.");
            throw  new InvalidRequestException("UserAccount can't be null.");
        }
        //check phone number already registered
        if(DatastoreUtility.findSavedUserAccount(userAccount.getPhoneNumber()) != null){
            throw  new InvalidRequestException("Already register PhoneNumber provided.");
        }
        if(userAccount.getCampingTripsKeys() == null){
            userAccount.setCampingTripsKeys(new ArrayList<Long>());
        }

        ofy().save().entity(userAccount).now();
    }

    @ApiMethod(name = "login")
    public UserAccount login(@Named("phoneNumber") long phoneNumber, @Named("password") String password) throws NotFoundException, InvalidRequestException{
        if(password == null || password.isEmpty()){
            throw  new InvalidRequestException("password can't be empty.");
        }

        UserAccount savedUserAccount = DatastoreUtility.findSavedUserAccount(phoneNumber);
        //check phone number already registered
        if(savedUserAccount == null){
            throw  new NotFoundException("Can't find user with PhoneNumber provided.");
        }

        if (savedUserAccount.getPassword().equalsIgnoreCase(password)){
            return savedUserAccount;
        }

        throw new NotFoundException("Unauthorized user");
    }

    @ApiMethod(name = "deletUser")
    public void deletUser(@Named("registeredPhoneNumber") long phoneNumber){
        //check non null and account exist.

        //deactivate useraccount

    }

}
