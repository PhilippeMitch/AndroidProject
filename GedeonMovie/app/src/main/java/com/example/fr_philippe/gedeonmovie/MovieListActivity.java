package com.example.fr_philippe.gedeonmovie;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.fr_philippe.gedeonmovie.models.Config;
import com.example.fr_philippe.gedeonmovie.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    // Constants
    // the base url for the API
    public final static String API_BASE_URL="https://api.themoviedb.org/3";

    // the parameter name for the api key
    public final static String API_KEY_PARAM="api_key";

    //the API key -- TODO move to a secure location
    //private final static String API_KEY="a07e22bc18f5cb106bfe4cc1f83ad8ed";

    //instants fields
    AsyncHttpClient client;

    // tag for login from this activity
    public final static String TAG="MovieListActivity";

    // the list of the current playing movies
    ArrayList<Movie> movies;
    // the recycler view
    RecyclerView rvMovies;
    // the adapter wired to the recycler view
    MovieAdapter adapter;
    //Image config
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        client=new AsyncHttpClient();
        // initialize the list of movies
        movies=new ArrayList<>();
        // initialize the adapter -- moview array connot be reinitialized after this point
        adapter= new MovieAdapter(movies);
        // resolve the recycler view and connect a loyout manager and adapter
        rvMovies=(RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
        // get the configuration on the creation
        getConfiguration();
    }

    // get list ef the current playing movies from the API
    private void getNowPlaying(){
        // create the url
        String url=API_BASE_URL + "/movie/now_playing";

        //set the request parameters
        RequestParams params= new RequestParams();

        // API Key, always required
        params.put(API_KEY_PARAM,getString(R.string.api_key));

        // execute a get request expecting a JSON object response
         client.get(url,params,new JsonHttpResponseHandler(){
             @Override
             public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                 // load the result into the movie list
                 try {
                     JSONArray result= response.getJSONArray("results");
                     // iterite through result set and create Movie objects
                     for(int i=0; i<result.length(); i++){
                         Movie movie= new Movie(result.getJSONObject(i));
                         movies.add(movie);
                         // notify adapter that a rows was added
                         adapter.notifyItemInserted(movies.size() - 1);
                     }
                     Log.i(TAG, String.format("Loaded %s movies",result.length()));
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                 logError("Failed to get data from now playing endpoint",throwable,true);
             }
         });
    }

    //get the configuration from the API
    private void getConfiguration(){
        // create the url
        String url=API_BASE_URL + "/configuration";

        //set the request parameters
        RequestParams params= new RequestParams();

        // API Key, always required
        params.put(API_KEY_PARAM,getString(R.string.api_key));

        // execute a get request expecting a JSON object response
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config=new Config(response);
                    Log.i(TAG,String.format("Loaded configuration with imageBaseUrl %s and posterSize %s",
                            config.getImageBaseUrl(),config.getPosterSize()));
                    // pass config to adapter
                    adapter.setConfig(config);
                    //get now the playing movie list
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed to parsing configuration",e,true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get configuration",throwable,true);
            }
        });
    }

    // handle error, log in alert user
    private void logError(String message,Throwable error, boolean alertUser){
        //always in the error
        Log.e(TAG,message,error);

        // alert user to avoid silent errors
        if(alertUser){
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        }
    }
}
