package com.codepath.apps.restclienttemplate.models;

import android.content.Context;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.loopj.android.http.JsonHttpResponseHandler;

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



    public void favorite(Context ctx, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(ctx).favorite(uid, favorited ^= true, handler);
        favoriteCount += favorited ? 1 : -1;

    }

    public void retweet(Context ctx, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(ctx).retweet(uid, retweeted ^= true, handler);
        retweetCount += retweeted ? 1 : -1;
    }


    public Tweet(){}


    //deserialize json
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        if(jsonObject.has("retweeted_status")) {
            // extract all data needed from root tweet
            tweet.retweetedBy = User.fromJSON(jsonObject.getJSONObject("user"));
            // discard root tweet (i.e. replace it with original tweet)
            jsonObject = jsonObject.getJSONObject("retweeted_status");
        }

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
