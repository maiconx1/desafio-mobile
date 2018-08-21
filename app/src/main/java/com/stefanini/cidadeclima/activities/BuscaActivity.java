package com.stefanini.cidadeclima.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.adapters.AdapterCidades;
import com.stefanini.cidadeclima.classes.Cidade;
import com.stefanini.cidadeclima.classes.Singleton;

import java.util.ArrayList;

public class BuscaActivity extends AppCompatActivity {
    private Holder holder;
    private ArrayList<Cidade> cidades;
    private AdapterCidades adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        Singleton.getInstance().mockCidades();
        holder = new BuscaActivity.Holder();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.txt_selecione);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BuscaActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.cidades = Singleton.getInstance().getCidades();
        adapter = new AdapterCidades(cidades, BuscaActivity.this);
        holder.getLstCidades().setAdapter(adapter);
        holder.getLstCidades().setLayoutManager(linearLayoutManager);
        holder.getSwpCidades().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                atualizaLista();
            }
        });
        atualizaLista();
    }
    
    private void atualizaLista() {
        adapter.setCidades(cidades);
        holder.getSwpCidades().setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class Holder {
        RecyclerView lstCidades;
        SwipeRefreshLayout swpCidades;

        Holder() {
            this.lstCidades = findViewById(R.id.lstCidades);
            this.swpCidades = findViewById(R.id.swpCidades);
        }

        public RecyclerView getLstCidades() {
            return lstCidades;
        }

        public SwipeRefreshLayout getSwpCidades() {
            return swpCidades;
        }
    }
}
