package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FR-PHILIPPE on 11/10/2017.
 */

public class User {
    // List of Attribute
    public  String name;
    public  long uid;
    public  String screanName;
    public  String profilImageUrl;

    public String tagLine;
    public int fallowersCount;
    public int fallowingCount;

    // deserialize from jason
    public static User fromJSON(JSONObject json) throws JSONException {
        User user= new User();

        // Extract and fill the values
        user.name=json.getString("name");
        user.uid=json.getLong("id");
        user.screanName=json.getString("screen_name");
        user.profilImageUrl=json.getString("profile_image_url");

        user.tagLine=json.getString("description");
        user.fallowersCount=json.getInt("followers_count");
        user.fallowingCount=json.getInt("friends_count");
        return user;
    }
}
