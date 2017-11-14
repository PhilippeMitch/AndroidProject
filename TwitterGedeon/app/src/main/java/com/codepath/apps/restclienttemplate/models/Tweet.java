package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FR-PHILIPPE on 11/10/2017.
 */

public class Tweet {
    // List out the attribute
    public String body;
    public  long uid;
    public  User user;
    public String createdAt;

    // Deserialize the json
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet=new Tweet();

        //Extract the value from json
        tweet.body=jsonObject.getString("text");
        tweet.uid=jsonObject.getLong("id");
        tweet.createdAt=jsonObject.getString("created_at");
        tweet.user=User.fromJSON(jsonObject.getJSONObject("user"));
        return  tweet;
    }
}

