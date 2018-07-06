package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvScreenName;
    private TextView tvBody;
    private ImageButton btXOut;
    private TextView tvLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        //Tweet result = (Tweet) getIntent().getSerializableExtra("result");

        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));


        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        btXOut = (ImageButton) findViewById(R.id.btXOut);
        tvLikes = (TextView) findViewById(R.id.tvLikes);



        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvScreenName.setText(" @" + tweet.getUser().getScreenName());


        // TODO- SET LIKES

        tvLikes.setText(String.valueOf(tweet.getFavoriteCount()));

        btXOut.setOnClickListener(this);

                Glide.with(this)
                        .load(tweet.user.profileImageUrl)
                        .into(ivProfileImage);

    }

    // TODO- add functionality for retweet and liking a tweet

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btReply:
////                 // Prepare data intent
//]                Intent data = new Intent();
//                // Pass relevant data back as a result
//                data.putExtra("name", etName.getText().toString());
//                data.putExtra("code", 200); // ints work too
//                // Activity finished ok, return the data
//                setResult(RESULT_OK, data); // set result code and bundle data for response
//                finish(); // closes the activity, pass data to parent

            case R.id.btXOut:
                this.finish();

            case R.id.btheart:

            case R.id.btRetweet:


        }
    }

}
