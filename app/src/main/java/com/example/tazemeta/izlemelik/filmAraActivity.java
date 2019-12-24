package com.example.tazemeta.izlemelik;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class filmAraActivity extends AppCompatActivity {
    private EditText filmAdi;
    private Button filmAraBtn;
    private ProgressBar searchProgress;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<movie> movieList;
    private RecyclerView.Adapter adapter;
    String apiKey = "&apikey=1e53119c";
    String apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_ara);
        filmAdi = findViewById(R.id.movieTitle);
        filmAraBtn = findViewById(R.id.searchBtn);
        searchProgress = findViewById(R.id.search_progress);
        searchProgress.setVisibility(View.INVISIBLE);


        filmAraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmAraBtn.setVisibility(View.INVISIBLE);
                searchProgress.setVisibility(View.VISIBLE);
                String film = filmAdi.getText().toString();
                if (film.isEmpty()) {
                    filmAraBtn.setVisibility(View.VISIBLE);
                    searchProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(filmAraActivity.this, "Lütfen bir film ismi giriniz.", Toast.LENGTH_SHORT).show();
                } else {
                    mList = findViewById(R.id.main_list);
                    movieList = new ArrayList<>();
                    adapter = new movieAdapter(getApplicationContext(), movieList);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

                    apiUrl = "http://www.omdbapi.com/?s=" + film + apiKey;
                    getData();
                   /* Intent i;
                    i= new Intent(getApplicationContext(),filmActivity.class);
                    i.putExtra("movie",film);*/
                    filmAraBtn.setVisibility(View.VISIBLE);
                    searchProgress.setVisibility(View.INVISIBLE);
                    //startActivity(i);
                    mList.setHasFixedSize(true);
                    mList.setLayoutManager(linearLayoutManager);
                    mList.addItemDecoration(dividerItemDecoration);
                    mList.setAdapter(adapter);

                }
            }
        });

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(filmAraActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object=null;
                JSONArray myobject=null;
                try{
                    object=new JSONObject(response);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                try{

                    myobject= object.getJSONArray("Search");
                    for(int i=0;i<myobject.length();i++)
                    {
                        JSONObject jsonObject = myobject.getJSONObject(i);
                        movie m=new movie();
                        m.setMtitle(jsonObject.getString("Title"));
                        m.setmYear(jsonObject.getString("Year"));
                        m.setmType(jsonObject.getString("Type"));
                        m.setmPosterUrl(jsonObject.getString("Poster"));
                        movieList.add(m);
                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    }
