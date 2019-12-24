package com.example.tazemeta.izlemelik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class filmActivity extends AppCompatActivity {

    String jsonData;
    private Button izle,begen,cop,izleme,begenme,copDegil;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    String uid= currentFirebaseUser.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final  DatabaseReference myRef = database.getReference("/"+uid);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        izleme=findViewById(R.id.izlemeBtn);
        begenme=findViewById(R.id.unFavBtn);
        copDegil=findViewById(R.id.copDegilBtn);
        izle = findViewById(R.id.izleBtn);
        begen=findViewById(R.id.favBtn);
        cop=findViewById(R.id.copBtn);
        izleme.setVisibility(View.INVISIBLE);
        izle.setVisibility(View.INVISIBLE);
        begenme.setVisibility(View.INVISIBLE);
        begen.setVisibility(View.INVISIBLE);
        copDegil.setVisibility(View.INVISIBLE);
        cop.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String movie = intent.getStringExtra("movie");
        String apiKey="&apikey=1e53119c";
        String apiUrl="http://www.omdbapi.com/?t="+movie+apiKey;
        getData(apiUrl);



    }
    public void getData(String api_url){
        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String response = "";
                try{
                    URL url = new URL(params[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader
                            reader = new
                            BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line = "";
                    while((line = reader.readLine()) != null){
                        response += line + "\n";
                    }
                } catch (Exception e){
                    return "Exception";
                }
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                filmActivity.this.jsonData = result;
                search();
            }
        };

        task.execute(api_url);
    }


    private void search() {
   final movie m = new movie();
    try{
        m.fill(this.jsonData);
        ListView listView = findViewById(R.id.list);
        ArrayList<String> movieDetails = new ArrayList<>();

        movieDetails.add("Adı: "+m.mtitle);
        movieDetails.add("Yılı: "+m.mYear);
        movieDetails.add("Tür: "+m.mGenre);
        movieDetails.add("Yönetmen: "+m.mDirector);
        movieDetails.add("Oyuncular: "+m.mActors);
        movieDetails.add("Yapım: "+m.mCountry);
        movieDetails.add("Süre: "+m.mRuntime);
        movieDetails.add("Imdb Puanı: "+m.mRating);
        movieDetails.add("Konu: "+m.mPlot);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,movieDetails);
        listView.setAdapter(adapter);


        ImageView posterImg = findViewById(R.id.imageView2);
        Glide.with(getApplicationContext())
                .load(m.mPosterUrl)
                .centerCrop()
                .into(posterImg);

            myRef.child("watched")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(m.mId)){
                            izleme.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            izle.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        myRef.child("unliked")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(m.mId)){
                            copDegil.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            cop.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        myRef.child("liked")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(m.mId)){
                            begenme.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            begen.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        izle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child("watched").child(m.mId).setValue(m.mtitle);
                Toast.makeText(filmActivity.this, "Listene eklendi.", Toast.LENGTH_SHORT).show();
                izle.setVisibility(View.INVISIBLE);
                izleme.setVisibility(View.VISIBLE);
            }
        });

        begen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("liked").child(m.mId).setValue(m.mtitle);
                Toast.makeText(filmActivity.this, "Sevdiklerine eklendi.", Toast.LENGTH_SHORT).show();
                begen.setVisibility(View.INVISIBLE);
                begenme.setVisibility(View.VISIBLE);


            }
        });

        cop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("unliked").child(m.mId).setValue(m.mtitle);
                Toast.makeText(filmActivity.this, "Sevmediklerine eklendi.", Toast.LENGTH_SHORT).show();
                cop.setVisibility(View.INVISIBLE);
                copDegil.setVisibility(View.VISIBLE);
            }
        });

        izleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("watched").child(m.mId).removeValue();
                Toast.makeText(filmActivity.this, "Listenden çıkarıldı.", Toast.LENGTH_SHORT).show();
                izleme.setVisibility(View.INVISIBLE);
                izle.setVisibility(View.VISIBLE);
            }
        });

        begenme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("liked").child(m.mId).removeValue();
                Toast.makeText(filmActivity.this, "Sevdiklerinden çıkarıldı.", Toast.LENGTH_SHORT).show();
                begenme.setVisibility(View.INVISIBLE);
                begen.setVisibility(View.VISIBLE);
            }
        });

        copDegil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("unliked").child(m.mId).removeValue();
                Toast.makeText(filmActivity.this, "Sevmediklerinden çıkarıldı.", Toast.LENGTH_SHORT).show();
                copDegil.setVisibility(View.INVISIBLE);
                cop.setVisibility(View.VISIBLE);
            }
        });

    } catch (JSONException e) {
        Toast.makeText(this,e.getMessage()+ "", Toast.LENGTH_SHORT).show();
    }
    }
}
