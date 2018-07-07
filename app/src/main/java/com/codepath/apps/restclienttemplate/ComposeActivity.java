package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ComposeActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    EditText etBody;
    TextView tvUsername;
    TextView tvCount;
    Button btCancel;
    Button btTweet;
    Tweet newTweet;


    private TwitterClient client;
    private final int COMPOSE_RESULT_CODE = 20;

    public void onCancelAction(View view) {
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);


        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        etBody = (EditText) findViewById(R.id.etBody);
        tvCount = (TextView) findViewById(R.id.tvCount);
        btTweet = (Button) findViewById(R.id.btTweet);
        btCancel = (Button) findViewById(R.id.btCancel);
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCount.setText(Integer.toString(140 - etBody.length()) + " char left");
                tvCount.setTextColor(140 - etBody.length() > 0 ? Color.GRAY : Color.RED);
            }
        });


        final RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15,15);
        final RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCornersTransformation);

        User.getCurrentUser(this, new User.UserCallbackInterface() {
            @Override
            public void onUserAvailable(User currentUser) {
                //load image w glide
                Glide.with(ComposeActivity.this)
                        .load(currentUser.profileImageUrl)
                        .apply(requestOptions)
                        .into(ivProfileImage);
                tvUsername.setText(currentUser.getScreenName());



            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelAction(v);
            }
        });

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            client.postTweet(etBody.getText().toString(),new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                    super.onSuccess(statusCode, headers, response);

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

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("DEBUG", errorResponse.toString());
                }

            });

            }

        });
    }
}




