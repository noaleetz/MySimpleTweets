package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;


    private TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    MenuItem pbProgressAction;

    private final int COMPOSE_REQUEST_CODE = 10;
    private final int COMPOSE_RESULT_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        client = TwitterApp.getRestClient(this);

        //find recycler view
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //init array list aka data source
        tweets = new ArrayList<>();
        //construct adapter FROM DATA
        tweetAdapter = new TweetAdapter(tweets);

        //rv setup includes (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(tweetAdapter);


        populateTimeline();


    }

    public void showProgressBar() {
        // Show progress item
        pbProgressAction.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        pbProgressAction.setVisible(false);
    }

    public void launchComposeView() {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        // instructions for passing data
        startActivityForResult(i, 0); // brings up the second activity
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK) {
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("newTweet"));
            Log.d("SendTweet", "Activity result: " + tweet.body);
            // Extract name value from result extras
            tweets.add(0, tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        }
    }

// TODO- Progress Bar


    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        pbProgressAction = menu.findItem(R.id.pbProgressAction);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(pbProgressAction);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        int id = item.getItemId();

        if (id == R.id.miCompose) {
            composeTweet();
            return true;

            // TODO- add profile button functionality to go to profile activity
            //} else if (id == R.id.miProfile) {
            //  profileView();
            //return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private void composeTweet() {

        if (isNetworkAvailable()) {
            // launches new activity to bring user to compose tweet screen
            launchComposeView();

        }

    }

    // TODO - Here: profileView() method


    private void populateTimeline() {

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //         Log.d("Twitter Client",response.toString());
                //iterate through json array
                //for e entry, get data from json object
                for (int i = 0; i < response.length(); i++) {
                    //convert e object to tweet model
                    //add tweet model to data source
                    //notify adapter of changes
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Twitter Client", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Twitter Client", responseString);
                throwable.printStackTrace();
            }
        });

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean networkConnection = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (!networkConnection) {
            Toast.makeText(TimelineActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return networkConnection;
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                // Remember to CLEAR OUT old items before appending in the new ones
                tweetAdapter.clear();
                tweets.clear();

                // ...the data has come back, add new items to your adapter...

                for (int i = 0; i < response.length(); i++) {


                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                    // Now we call setRefreshing(false) to signal refresh has finished

                }

                tweetAdapter.addAll(tweets);

                swipeContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }

        });
    }

// TODO- setup listeners




}
