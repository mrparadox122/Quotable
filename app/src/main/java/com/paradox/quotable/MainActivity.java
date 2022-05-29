package com.paradox.quotable;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    PostAdapter postAdapter;
    List<Results> postsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(this);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if (connected)
        {
            fetchPost();
        }
        else if (!connected)
        {
            Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show();
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String s1 = sh.getString("localdb", "");
            try {
                JSONObject obj = new JSONObject(s1);
                JSONArray arr = obj.getJSONArray("results");
                for (int i = 0; i < arr.length(); i++)
                {
                    String formattedString = arr.getJSONObject(i).getString("tags").replace(","," ").replace("[","").replace("]","").trim();
                    Results results = new Results();
                    results.setAuthor(arr.getJSONObject(i).getString("author"));
                    results.setAuthorSlug(arr.getJSONObject(i).getString("authorSlug"));
                    results.setContent(arr.getJSONObject(i).getString("content"));
                    results.setTags(formattedString);
                    postsList.add(results);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    postAdapter = new PostAdapter(postsList,getApplicationContext());
                    recyclerView.setAdapter(postAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.GONE);
        }


    }

    private void fetchPost()
    {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getPosts().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //local db using sharedPreferences :)
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("localdb", response.body().toString());
                myEdit.commit();
                try {
                    // getting json object
                    JSONObject obj = new JSONObject(response.body().toString());
                    JSONArray arr = obj.getJSONArray("results");
                    //for loop to extract string
                    for (int i = 0; i < arr.length(); i++)
                    {
                        String formattedString = arr.getJSONObject(i).getString("tags").replace(","," ").replace("[","").replace("]","").trim();
                        Results results = new Results();
                        results.setAuthor(arr.getJSONObject(i).getString("author"));
                        results.setAuthorSlug(arr.getJSONObject(i).getString("authorSlug"));
                        results.setContent(arr.getJSONObject(i).getString("content"));
                        results.setTags(formattedString);
                        postsList.add(results);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        postAdapter = new PostAdapter(postsList,getApplicationContext());
                        recyclerView.setAdapter(postAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.e(TAG, "onFailure: "+t.getMessage() );


            }
        });
    }



}