package com.avgtechie.gocampingbackend.utils;

import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.PhoneVerification;
import com.authy.api.Verification;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fob966 on 4/23/16.
 */
public class TwilioClient {

    private static final String TEST_ACCOUNT_SID = "ACe89157aa2fc144b9562a3e7621c7ac8c";
    private static final String TEST_AUTH_TOKEN = "73f3ba3818dd56782f133b151f41ddb0";

    private static final String AUTHY_API_KEY = "";

    public static void sendMessage() {
        TwilioRestClient client = new TwilioRestClient(TEST_ACCOUNT_SID, TEST_AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+15104617034"));
        params.add(new BasicNameValuePair("From", "+15005550006"));
        params.add(new BasicNameValuePair("Body", "Hi Test Credential No Charge"));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = null;
        try {
            message = messageFactory.create(params);
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        System.out.println(message.toString());

    }


    public static void sendVerificationCode(String toPhoneNumber) throws IllegalArgumentException {
        String formattedPhoneNumber = Utility.formatPhoneNumber(toPhoneNumber);
        if (formattedPhoneNumber == null) {
            throw new IllegalArgumentException("Provided phone number is not valid.It must be 10 digit.");
        }

        AuthyApiClient client = new AuthyApiClient(AUTHY_API_KEY);
        PhoneVerification phoneVerification = client.getPhoneVerification();

        Verification verification;
        Params params = new Params();
        params.setAttribute("locale", "en");

        verification = phoneVerification.start(formattedPhoneNumber, "1", "sms", params);

        System.out.println(verification.getMessage());
        System.out.println(verification.getIsPorted());
        System.out.println(verification.getSuccess());
        System.out.println(verification.isOk());
    }


    public static void checkVerificationCode(String toPhoneNumber, String verificationCode) {
        String formattedPhoneNumber = Utility.formatPhoneNumber(toPhoneNumber);

        if (formattedPhoneNumber == null) {
            throw new IllegalArgumentException("Provided phone number is not valid.It must be 10 digit.");
        }


        AuthyApiClient client = new AuthyApiClient("SomeApiKey");
        PhoneVerification phoneVerification = client.getPhoneVerification();

        Verification verification;
        verification = phoneVerification.check(formattedPhoneNumber, "1", verificationCode);

        System.out.println(verification.getMessage());
        System.out.println(verification.getIsPorted());
        System.out.println(verification.getSuccess());
    }


}
