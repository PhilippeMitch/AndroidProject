package com.codepath.apps.restclienttemplate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by FR-PHILIPPE on 11/11/2017.
 */

public class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener{

    public interface TweetSelectedListener{
        // Handle tweet selected
        public void onTweetSelected(Tweet tweet);

    }
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    // inflation happens inside createView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragments_tweets_list,container,false);
        // find the recycleView
        rvTweets=(RecyclerView)v.findViewById(R.id.rvTweet);
        // init the arraylist (data source)
        tweets=new ArrayList<>();
        // Construct the adapter from data source
        tweetAdapter = new TweetAdapter(tweets,this);
        //RecycleView setup (Layout manager, user adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvTweets.setAdapter(tweetAdapter);
        return v;
    }

    public void addItems(JSONArray response){
        for(int i=0; i<response.length(); i++){
            // convert each object to a tweet model
            // add the tweet model to our data source
            // notify the adapter that we've added an item
            Tweet tweet= null;
            try {
                tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() -1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet=tweets.get(position);
        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }
}
