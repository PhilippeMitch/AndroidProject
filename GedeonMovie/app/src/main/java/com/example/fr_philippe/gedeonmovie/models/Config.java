package com.example.fr_philippe.gedeonmovie.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FR-PHILIPPE on 10/11/2017.
 */

public class Config {
    // the base url for loading image
    String imageBaseUrl;
    // the poster size use when fetching images, part of the url
    String posterSize;
    // the backdrap size use when fetching image
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images=object.getJSONObject("images");
        // get the image base url
        imageBaseUrl=images.getString("secure_base_url");
        //get the poster size
        JSONArray posterSizeOptions= images.getJSONArray("poster_sizes");
        //use the option at index 3 or 342 as a fallback
        posterSize= posterSizeOptions.optString(3,"w342");
        // parse the backdropsizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOption=images.getJSONArray("backdrop_sizes");
        backdropSize=backdropSizeOption.optString(1,"w780");
    }
    // help method for create urls
    public String getImageUrl(String size,String path){
        return String.format("%s%s%s",imageBaseUrl,size,path);//concatenate all three
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {return backdropSize;}
}
