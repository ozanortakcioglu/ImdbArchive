package com.example.tazemeta.izlemelik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView filmAra,favs,unlike,random,list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        filmAra =findViewById(R.id.filmAra);
        favs =findViewById(R.id.favoriler);
        unlike =findViewById(R.id.begenmediklerim);
        random =findViewById(R.id.rastgele);
        list =findViewById(R.id.liste);
        filmAra.setOnClickListener(this);
        favs.setOnClickListener(this);
        unlike.setOnClickListener(this);
        random.setOnClickListener(this);
        list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.filmAra : i = new Intent(this,com.example.tazemeta.izlemelik.filmAraActivity.class); startActivity(i);break;
            case R.id.favoriler : i = new Intent(this,com.example.tazemeta.izlemelik.favoriler.class);startActivity(i);break;
            case R.id.begenmediklerim : i = new Intent(this,com.example.tazemeta.izlemelik.begenmediklerim.class);startActivity(i);break;
            case R.id.rastgele : i = new Intent(this,com.example.tazemeta.izlemelik.rastgele.class);startActivity(i);break;
            case R.id.liste : i = new Intent(this,com.example.tazemeta.izlemelik.liste.class);startActivity(i);break;
        }

    }
}
