package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

    //list attributes
    public String body;
    public long uid; //database
    public String createdAt;
    public User user;

    //deserialize json
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //get info from json
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong ("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;







    }

}
