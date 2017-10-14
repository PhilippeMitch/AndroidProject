package com.example.fr_philippe.gedeonmovie.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FR-PHILIPPE on 10/9/2017.
 */

public class Movie {
    // values from the API
    private String title;
    private String overView;
    private String posterpath; //only the path
    private String backdropPath;

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    //Initialize from JSON data
    public Movie(JSONObject object) throws JSONException{
        title=object.getString("title");
        overView=object.getString("overview");
        posterpath=object.getString("poster_path");
        backdropPath=object.getString("backdrop_path");

    }
}
