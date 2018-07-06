package com.codepath.apps.restclienttemplate.models;

import android.content.Context;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import cz.msebera.android.httpclient.Header;

@Parcel
public class User {

    // list attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public boolean isVerified;

    public String getName(){ return name; }
    public long getUid(){ return uid; }
    public String getScreenName(){ return screenName; }
    public String getProfileImageUrl(){ return profileImageUrl; }
    public boolean isVerified(){ return isVerified; }


    public static User currentUser;

    public User(){}

    //get data from json
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.isVerified = json.getBoolean("verified");




        return user;



    }

    public interface UserCallbackInterface {
        void onUserAvailable(User currentUser);
    }

    public static void getCurrentUser(Context context, final UserCallbackInterface handler) {
        TwitterClient client = TwitterApp.getRestClient(context);
        client.verifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    handler.onUserAvailable(User.fromJSON(response));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}








