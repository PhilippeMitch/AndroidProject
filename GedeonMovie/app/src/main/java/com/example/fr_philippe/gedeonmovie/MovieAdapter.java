package com.example.fr_philippe.gedeonmovie;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fr_philippe.gedeonmovie.models.Config;
import com.example.fr_philippe.gedeonmovie.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by FR-PHILIPPE on 10/9/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // List of movies
    ArrayList<Movie> movies;
    //config needed for image urls
    Config config;
    //context for rending
    Context context;
    // initialize with list
    public MovieAdapter(ArrayList<Movie> movies){
        this.movies=movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    // create and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        //create the view using the master movie layout
        View movieView= inflater.inflate(R.layout.item_movie,parent,false);
        // return a new view holder
        return  new ViewHolder(movieView);
    }

    // binds an interflate view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the movie data at the specific position
        Movie movie= movies.get(position);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverView.setText(movie.getOverView());

        // determine the current orientation
        boolean isPortrait=context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT;

        //Build url for poster image
        String imageUrl=null;

        // if in portrait mode, load the poster
        if(isPortrait){
            imageUrl=config.getImageUrl(config.getPosterSize(),movie.getPosterpath());
        }else{
            // load the backdropImage
            imageUrl=config.getImageUrl(config.getBackdropSize(),movie.getBackdropPath());
        }

        // get the correct placeholder and image view for the current orientation
        int placeholderId= isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView= isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;

        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context,25,0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);

    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // create the viewholder a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{

        //track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverView;

        public ViewHolder(View itemView) {
            super(itemView);

            //lookup view objects by id
            ivPosterImage=(ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView)itemView.findViewById(R.id.ivBackdropImage);
            tvOverView=(TextView) itemView.findViewById(R.id.tvOverView);
            tvTitle= (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
