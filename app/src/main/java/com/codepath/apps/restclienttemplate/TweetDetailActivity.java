package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TweetDetailActivity extends AppCompatActivity {

    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvScreenName;
    private TextView tvBody;
    private ImageButton btXOut;
    private TextView tvLikes;
    private TextView tvRetweets;
    private EditText etReply;
    private ImageButton btReply;
    private ImageButton btRetweet;
    private ImageButton btHeart;

    private TwitterClient client;
    private final int COMPOSE_RESULT_CODE = 20;
    private Tweet replyTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        client = TwitterApp.getRestClient(this);


        //Tweet result = (Tweet) getIntent().getSerializableExtra("result");

        final Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));


        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        btXOut = (ImageButton) findViewById(R.id.btXOut);
        tvLikes = (TextView) findViewById(R.id.tvRetweets);
        tvRetweets = (TextView) findViewById(R.id.tvRetweets);
        etReply = (EditText) findViewById(R.id.etReply);
        btReply = (ImageButton) findViewById(R.id.btReply);
        btRetweet = (ImageButton) findViewById(R.id.btRetweet);
        btHeart = (ImageButton) findViewById(R.id.btHeart);




        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvScreenName.setText(" @" + tweet.getUser().getScreenName());
        etReply.setText(" @" + tweet.getUser().getScreenName());



        tvLikes.setText(String.valueOf(tweet.getFavoriteCount()));
        tvRetweets.setText(String.valueOf(tweet.getRetweetCount()));

        btXOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // TODO- favorite and retweet
 //       /*
        btRetweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tweet.favorite(TweetDetailActivity.this, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        

                            tvLikes.setText(String.valueOf(tweet.getFavoriteCount()));

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("DEBUG", errorResponse.toString());
                    }

                });
            }
        });

        btHeart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tweet.retweet(TweetDetailActivity.this, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        tvLikes.setText(String.valueOf(tweet.getFavoriteCount()));


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("DEBUG", errorResponse.toString());
                    }

                });
            }
        });

//*/
        btReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long inReplyTo = -1;
                if(replyTo != null) {
                    inReplyTo = replyTo.getId();
                }
                client.ReplyToTweet(etReply.getText().toString(), inReplyTo, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Tweet newTweet = new Tweet();
                        try {
                            newTweet = Tweet.fromJSON(response);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        Intent i = new Intent();
                        i.putExtra("newTweet", Parcels.wrap(newTweet));
                        setResult(RESULT_OK, i);
                        finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("DEBUG", errorResponse.toString());
                    }
                });
            }
        });
        final RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15,15);
        final RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCornersTransformation);

        Glide.with(this)
                        .load(tweet.user.profileImageUrl)
                        .apply(requestOptions)
                        .into(ivProfileImage);

    }

}
