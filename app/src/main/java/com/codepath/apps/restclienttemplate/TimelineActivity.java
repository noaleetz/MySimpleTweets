package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

   private TwitterClient  client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        //find recycler view
        rvTweets = (RecyclerView)  findViewById(R.id.rvTweet);
        //init array list aka data source
        tweets = new ArrayList<>();
        //construct adapter FROM DATA
        tweetAdapter = new TweetAdapter(tweets);

        //rv setup includes (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(tweetAdapter);



        populateTimeline();


    }

    private void populateTimeline() {

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
       //         Log.d("Twitter Client",response.toString());
                //iterate through json array
                //for e entry, get data from json object
                for (int i =0;i<response.length();i++){
                    //convert e object to tweet model
                    //add tweet model to data source
                    //notify adapter of changes
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i)) ;
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size()-1);
                    }
                    catch(JSONException e){
                        e.printStackTrace();

                        }

                    }
                }








            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               Log.d("Twitter Client",response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Twitter Client",errorResponse.toString());
                throwable.printStackTrace();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Twitter Client",errorResponse.toString());
                throwable.printStackTrace();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Twitter Client",responseString);
                throwable.printStackTrace();            }
        });

    }
}
