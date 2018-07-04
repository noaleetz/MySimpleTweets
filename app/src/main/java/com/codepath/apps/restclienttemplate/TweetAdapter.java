package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    // 1- pass in tweets array (give adapter data)

    private List<Tweet> mtweets;
    Context context;

    public TweetAdapter(List<Tweet> tweets) {
        mtweets = tweets;




    }

    //2- inflate EACH row, cache refs into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet,parent,false);

        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    //3- bind value based on pos of element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to pos
        Tweet tweet = mtweets.get(position);
        //populate views w this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        //load image w glide
        Glide.with(holder.itemView.getContext())
                .load(tweet.user.profileImageUrl)
                .into(holder.ivProfileImage);

        // TODO - parse timestamp to abbreivate "minutes ago" to "m"

        holder.tvCreatedAt.setText(getRelativeTimeAgo(tweet.createdAt));


    }

    @Override
    public int getItemCount() {
        return mtweets.size();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    //4- make a ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvCreatedAt;


        public ViewHolder(View itemView) {
            super(itemView);

            //findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAgo);








        }

    }
}
