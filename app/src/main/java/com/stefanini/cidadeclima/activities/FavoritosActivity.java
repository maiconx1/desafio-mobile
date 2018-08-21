package com.stefanini.cidadeclima.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.adapters.AdapterFavoritos;
import com.stefanini.cidadeclima.classes.Favorito;
import com.stefanini.cidadeclima.classes.OpenWeatherJson;
import com.stefanini.cidadeclima.classes.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class FavoritosActivity extends AppCompatActivity {
    private Holder holder;
    private AdapterFavoritos adapter;
    private ArrayList<Favorito> favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        Singleton.getInstance().mockFavoritos();
        holder = new Holder();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.txt_favoritos);

        holder.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(FavoritosActivity.this, BuscaActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoritosActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.favoritos = Singleton.getInstance().getFavoritos();
        adapter = new AdapterFavoritos(favoritos, FavoritosActivity.this);
        holder.getLstFavoritos().setAdapter(adapter);
        holder.getLstFavoritos().setLayoutManager(linearLayoutManager);
        holder.getSwpFavoritos().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                atualizaLista();
            }
        });
        atualizaLista();
    }

    private void atualizaLista() {
        adapter.setFavoritos(favoritos);
        holder.getSwpFavoritos().setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaLista();
    }

    class Holder {
        RecyclerView lstFavoritos;
        FloatingActionButton fab;
        SwipeRefreshLayout swpFavoritos;

        Holder() {
            this.lstFavoritos = findViewById(R.id.lstFavoritos);
            this.fab = findViewById(R.id.fab);
            this.swpFavoritos = findViewById(R.id.swpFavoritos);
        }

        public RecyclerView getLstFavoritos() {
            return lstFavoritos;
        }

        public FloatingActionButton getFab() {
            return fab;
        }

        public SwipeRefreshLayout getSwpFavoritos() {
            return swpFavoritos;
        }
    }
}
