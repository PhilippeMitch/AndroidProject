package com.codepath.apps.restclienttemplate.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by FR-PHILIPPE on 11/11/2017.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    // return the total # of Fragment
    private String tabTitle[]= new String[]{"Homme","Mentions"};
    private Context context;
    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context=context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new HomeTimelineFragment();
        }else if(position==1){
            return  new MentionsTimelineFragment();
        }else {
            return  null;
        }
    }

    // return the fragment title


    @Override
    public CharSequence getPageTitle(int position) {
        //ggenerate title based on item position
        return tabTitle[position];
    }
}
