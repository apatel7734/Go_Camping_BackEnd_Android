package com.avgtechie.gocampingbackend;

import com.avgtechie.gocampingbackend.objectifymodels.CampingTrip;
import com.avgtechie.gocampingbackend.objectifymodels.Expense;
import com.avgtechie.gocampingbackend.objectifymodels.Family;
import com.avgtechie.gocampingbackend.objectifymodels.Member;
import com.avgtechie.gocampingbackend.objectifymodels.NewUsersTripInvites;
import com.avgtechie.gocampingbackend.objectifymodels.UserAccount;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 *
 */
public class OfyService {

    static {
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(CampingTrip.class);
        ObjectifyService.register(Family.class);
        ObjectifyService.register(Expense.class);
        ObjectifyService.register(Member.class);
        ObjectifyService.register(UserAccount.class);
        ObjectifyService.register(NewUsersTripInvites.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
