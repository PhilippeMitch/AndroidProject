package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by FR-PHILIPPE on 11/10/2017.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    //pass inthe Thweets array in the constructor
    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;
    // define a interface required by the viewHolder
    public interface TweetAdapterListener{
        public void onItemSelected(View view,int position);
    }
    public TweetAdapter(List<Tweet> tweets,TweetAdapterListener listener){
        mTweets=tweets;
        mListener=listener;

    }
    //for each row, inflate the layout and cache references into viewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet,parent,false);
        ViewHolder viewHolder=new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        Tweet tweet=mTweets.get(position);
        //populate the views according to this data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        Glide.with(context).load(tweet.user.profilImageUrl).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create viewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;

        public ViewHolder(View itemView){
            super(itemView);

            //perform  findViewById lookups
            ivProfileImage=(ImageView)itemView.findViewById(R.id.ivProfileImage);
            tvUserName=(TextView) itemView.findViewById(R.id.tvUserName);
            tvBody=(TextView)itemView.findViewById(R.id.tvBody);

            // handle row click event
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(mListener !=null) {
                        // get the position of the row element
                        int position=getAdapterPosition();
                        // fire the listener call back
                        mListener.onItemSelected(view,position);
                    }
                }
            });
        }
    }

}
