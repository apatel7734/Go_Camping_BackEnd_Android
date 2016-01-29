package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.log.InvalidRequestException;
import com.googlecode.objectify.Key;

import java.util.logging.Logger;

import static com.avgtechie.gocampingbackend.OfyService.*;


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
        if(this.findUserAccount(userAccount.getPhoneNumber()) != null){
            throw  new InvalidRequestException("Already register PhoneNumber provided.");
        }

        ofy().save().entity(userAccount).now();
    }
    @ApiMethod(name = "deletUser")
    public void deletUser(@Named("registeredPhoneNumber") long phoneNumber){
        //check non null and account exist.

        //deactivate useraccount

    }

    private UserAccount findUserAccount(final long phoneNumber) {
        return ofy().load().key(Key.create(UserAccount.class, phoneNumber)).now();
    }

}
