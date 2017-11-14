package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragment.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName= getIntent().getStringExtra("screen_name");
        // Create the use fragment
        UserTimelineFragment userTimelineFragment= new UserTimelineFragment().newInstance(screenName);
        // display the user timeline inside the contenair (dinamicaly)
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();

        // make chage here
        ft.replace(R.id.llCount,userTimelineFragment);
        // commit
        ft.commit();

        client=TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // deserialize the user object
                User user= null;
                try {
                    user = User.fromJSON(response);
                    // set the title of the actionbar base on the user information
                    getSupportActionBar().setTitle(user.screanName);
                    //populate the user headline
                    populateUserHeadline(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void populateUserHeadline(User user){
        TextView tvName=(TextView)findViewById(R.id.tvUserName);
        TextView tvTagLine=(TextView)findViewById(R.id.tvTagLine);
        TextView tvFallowers=(TextView)findViewById(R.id.tvFallowers);
        TextView tvFallowing=(TextView)findViewById(R.id.tvFallowing);

        ImageView ivProfileImage=(ImageView)findViewById(R.id.ivProfileImage);

        tvName.setText(user.name);

        tvTagLine.setText(user.tagLine);
        tvFallowers.setText(user.fallowersCount + "Followers");
        tvFallowing.setText(user.fallowingCount + "Following");
        // Load profile image with glide
        Glide.with(this).load(user.profilImageUrl).into(ivProfileImage);

    }
}
