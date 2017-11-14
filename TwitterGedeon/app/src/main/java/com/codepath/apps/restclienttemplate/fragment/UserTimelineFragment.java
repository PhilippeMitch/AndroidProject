package com.codepath.apps.restclienttemplate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by FR-PHILIPPE on 11/11/2017.
 */

public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    public static UserTimelineFragment newInstance(String screeName){
        UserTimelineFragment userTimelineFragment=new UserTimelineFragment();
        Bundle args=new Bundle();
        args.putString("screen_name",screeName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client= TwitterApp.getRestClient();
        populateTimeline();
    }

    public void populateTimeline(){
        // comes from the activity
        String screenName=getArguments().getString("screen_name");
        client.getUserTimeline(screenName,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient",response.toString());
                // iterate through the Json Array
                // for each entry, deserialize the json Object
                addItems(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient",response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient",responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient",errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient",errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
