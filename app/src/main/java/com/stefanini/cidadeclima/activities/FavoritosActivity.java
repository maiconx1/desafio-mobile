package com.stefanini.cidadeclima.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.adapters.AdapterFavoritos;
import com.stefanini.cidadeclima.classes.Constants;
import com.stefanini.cidadeclima.classes.Favorito;
import com.stefanini.cidadeclima.classes.ListaOpenWeather;
import com.stefanini.cidadeclima.classes.Singleton;
import com.stefanini.cidadeclima.classes.Utils;
import com.stefanini.cidadeclima.database.FavoritosDbHelper;
import com.stefanini.cidadeclima.database.FavoritosReaderContract;

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
        holder = new Holder();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pegaFavoritos();

        toolbar.setTitle(R.string.txt_favoritos);

        holder.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoritosActivity.this, BuscaActivity.class);
                startActivity(intent);
            }
        });

        this.favoritos = Singleton.getInstance().getFavoritos();
    }

    private void pegaFavoritos() {
        FavoritosDbHelper dbHelper = new FavoritosDbHelper(FavoritosActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                FavoritosReaderContract.Favorito.COLUMN_ID,
                FavoritosReaderContract.Favorito.COLUMN_NOME
        };
        String sort = FavoritosReaderContract.Favorito.COLUMN_NOME + " ASC";
        Cursor cursor = db.query(
                FavoritosReaderContract.Favorito.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sort
        );
        while(cursor.moveToNext()) {
            Singleton.getInstance().addFavorito(new Favorito(cursor.getInt(cursor.getColumnIndexOrThrow(FavoritosReaderContract.Favorito.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FavoritosReaderContract.Favorito.COLUMN_NOME))));
        }
        cursor.close();
        dbHelper.close();
    }

    private void atualizaLista() {
        holder.getLstFavoritos().setVisibility(View.GONE);
        if(Utils.isConectado(FavoritosActivity.this)) {
            holder.getSwpFavoritos().setRefreshing(true);
            ClimaAssincrono assincrono = new ClimaAssincrono();
            StringBuilder ids = new StringBuilder();
            for (Favorito fav : favoritos) {
                ids.append((ids.length() == 0) ? "" : ",").append(fav.getId());
            }
            assincrono.execute(Constants.URL + Constants.METODO[Constants.GROUP] + "?id=" + ids + Constants.FINAL);
        }
        else {
            holder.getSwpFavoritos().setRefreshing(false);
            AlertDialog.Builder alert  = new AlertDialog.Builder(this);
            alert.setMessage(R.string.war_sem_conexao);
            alert.setTitle(R.string.tit_aviso);
            alert.setPositiveButton(R.string.txt_ok, null);
            alert.create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(favoritos.size() > 0) {
            holder.getTxtVazio().setVisibility(View.GONE);
            holder.getSwpFavoritos().setVisibility(View.VISIBLE);
            adapter = new AdapterFavoritos(favoritos, FavoritosActivity.this);
            holder.getLstFavoritos().setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoritosActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.getLstFavoritos().setLayoutManager(linearLayoutManager);
            holder.getSwpFavoritos().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    atualizaLista();
                }
            });
            atualizaLista();
        }
        else {
            holder.getSwpFavoritos().setVisibility(View.GONE);
            holder.getTxtVazio().setVisibility(View.VISIBLE);
        }
    }

    class Holder {
        RecyclerView lstFavoritos;
        FloatingActionButton fab;
        SwipeRefreshLayout swpFavoritos;
        TextView txtVazio;

        Holder() {
            this.lstFavoritos = findViewById(R.id.lstFavoritos);
            this.fab = findViewById(R.id.fab);
            this.swpFavoritos = findViewById(R.id.swpFavoritos);
            this.txtVazio = findViewById(R.id.txtVazio);
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

        public TextView getTxtVazio() {
            return txtVazio;
        }
    }

    class ClimaAssincrono extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try
            {
                if(!s.isEmpty())
                {
                    Gson gson = new Gson();
                    ListaOpenWeather climas = gson.fromJson(s, ListaOpenWeather.class);
                    for(int i = 0;i<favoritos.size();i++) {
                        favoritos.get(i).setClima(climas.getLista().get(i).getClima().get(0).getDescricao());
                        favoritos.get(i).setTemperatura(climas.getLista().get(i).getMain().getTemperatura());
                    }
                    adapter.setFavoritos(favoritos);
                    holder.getSwpFavoritos().setRefreshing(false);
                    holder.getLstFavoritos().setVisibility(View.VISIBLE);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            HttpsURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                URL url = new URL(params[0]);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");

                }

                return buffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
