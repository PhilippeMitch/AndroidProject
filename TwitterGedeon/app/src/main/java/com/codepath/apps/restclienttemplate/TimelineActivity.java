package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragment.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragment.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;


public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get the view pager
        ViewPager vPager= (ViewPager)findViewById(R.id.viewPager);
        //set the adapter for the pager
        vPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(),this));
        //setup the tablayout to ise the view pager
        TabLayout tabLayout=(TabLayout)findViewById(R.id.slinding_tabs);
        tabLayout.setupWithViewPager(vPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        return  true;
    }

    public void onProfileView(MenuItem item){
        // launch the profile view
        Intent i= new Intent(this,ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Toast.makeText(this,tweet.body,Toast.LENGTH_SHORT).show();
    }
}
