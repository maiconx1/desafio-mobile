package com.stefanini.cidadeclima.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.stefanini.cidadeclima.R;

public class FavoritosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        Holder holder = new Holder();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        holder.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(FavoritosActivity.this, BuscaActivity.class);
                startActivity(intent);
            }
        });
    }

    class Holder {
        RecyclerView lstFavoritos;
        FloatingActionButton fab;

        Holder() {
            this.lstFavoritos = findViewById(R.id.lstFavoritos);
            this.fab = findViewById(R.id.fab);
        }

        public RecyclerView getLstFavoritos() {
            return lstFavoritos;
        }

        public FloatingActionButton getFab() {
            return fab;
        }
    }
}
