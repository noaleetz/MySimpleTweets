package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
public class Tweet {

    //list attributes
    public String body;
    public long uid; //database
    public String createdAt;
    public User user;

    private User retweetedBy;

    private long retweetCount;
    private boolean retweeted;

    private long favoriteCount;
    private boolean favorited;

    private String mediaUrl = "";

    public long getId() {
        return uid;
    }
    public User getUser() {
        return user;
    }
    public String getBody() {
        return body;
    }
    public long getRetweetCount() {
        return retweetCount;
    }
    public long getFavoriteCount() {
        return favoriteCount;
    }
    public boolean isRetweeted() {
        return retweeted;
    }
    public boolean isFavorited() {
        return favorited;
    }
    public User getRetweetedBy() {
        return retweetedBy;
    }
    public String getMediaUrl() {
        return mediaUrl;
    }

    public Tweet(){}


    //deserialize json
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //get info from json
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong ("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        tweet.retweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        tweet.favoriteCount = jsonObject.getLong("favorite_count");

        return tweet;







    }

}
