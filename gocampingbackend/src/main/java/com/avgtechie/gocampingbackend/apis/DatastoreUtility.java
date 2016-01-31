package com.avgtechie.gocampingbackend.apis;

import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.googlecode.objectify.Key;

import static com.avgtechie.gocampingbackend.OfyService.ofy;

/**
 * Created by fob966 on 1/30/16.
 */
public class DatastoreUtility{


    static UserAccount findSavedUserAccount(final long phoneNumber) {
        return ofy().load().key(Key.create(UserAccount.class, phoneNumber)).now();
    }
}
