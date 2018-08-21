package com.stefanini.cidadeclima.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.classes.Cidade;
import com.stefanini.cidadeclima.classes.Favorito;
import com.stefanini.cidadeclima.classes.OpenWeatherJson;
import com.stefanini.cidadeclima.classes.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class DetalhesActivity extends AppCompatActivity {
    private enum Imagens{
        i01d(R.drawable.i01d), i01n(R.drawable.i01n), i02d(R.drawable.i02d), i02n(R.drawable.i02n),
        i03d(R.drawable.i03d), i03n(R.drawable.i03n), i04d(R.drawable.i04d), i04n(R.drawable.i04n),
        i09d(R.drawable.i09d), i09n(R.drawable.i09n), i10d(R.drawable.i10d), i10n(R.drawable.i10n),
        i11d(R.drawable.i11d), i11n(R.drawable.i11n), i13d(R.drawable.i13d), i13n(R.drawable.i13n),
        i50d(R.drawable.i50d), i50n(R.drawable.i50n);

        private final int imagem;

        Imagens(int imagem) {
            this.imagem = imagem;
        }

        public int getImagem() {
            return imagem;
        }
    }

    private Holder holder;
    private boolean favoritado;
    private int id = 0;
    private String nome = "";
    private Menu menu;
    private ArrayList<Integer> ids;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        locale = getResources().getConfiguration().locale;

        holder = new Holder();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.txt_detalhes);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ids = new ArrayList<>();

        Intent intent = getIntent();
        if(intent != null) {
            Gson gson = new Gson();
            Favorito favorito;
            Cidade cidade;
            String json = intent.getStringExtra("FAVORITO");
            String clima = "", temperatura = "", minMax = "";
            if(json == null || json.isEmpty()) {
                json = intent.getStringExtra("CIDADE");
                cidade = gson.fromJson(json, Cidade.class);
                id = cidade.getId();
                nome = cidade.getNome();
            }
            else
            {
                favorito = gson.fromJson(json, Favorito.class);
                id = favorito.getId();
                nome = favorito.getNome();
            }

            for(Favorito fav : Singleton.getInstance().getFavoritos()) {
                if(fav.getId() == id) {
                    favoritado = true;
                }
            }
            ids.add(id);
            holder.getTxtCidade().setText(nome);
            holder.getTxtClima().setText(clima);
            holder.getTxtTemperatura().setText(temperatura);
            holder.getTxtMaxMin().setText(minMax);
            holder.getSwpDetalhes().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    atualizaDetalhes();
                }
            });

            atualizaDetalhes();
        }
    }

    private void atualizaDetalhes() {
        ClimaAssincrono assincrono = new ClimaAssincrono();
        assincrono.execute("https://api.openweathermap.org/data/2.5/weather?id=" + ids.get(0) + "&appid=2bac87e0cb16557bff7d4ebcbaa89d2f&lang=pt&units=metric");// + ids);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes, menu);
        this.menu = menu;
        if(favoritado) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_heart));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.favorito:
                if(favoritado) {
                    for(Favorito fav : Singleton.getInstance().getFavoritos()) {
                        if(fav.getId() == id) {
                            Singleton.getInstance().getFavoritos().remove(fav);
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_heart_outline));
                            break;
                        }
                    }
                }
                else {
                    Singleton.getInstance().addFavorito(new Favorito(id, nome));
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_heart));
                }
                favoritado = !favoritado;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class Holder {
        private TextView txtCidade, txtClima, txtTemperatura, txtMaxMin, txtHorario;
        private ImageView imgClima;
        private LinearLayout lnTudo;
        private SwipeRefreshLayout swpDetalhes;

        Holder() {
            txtCidade = findViewById(R.id.txtCidade);
            txtClima = findViewById(R.id.txtClima);
            txtTemperatura = findViewById(R.id.txtTemperatura);
            txtMaxMin = findViewById(R.id.txtMaxMin);
            txtHorario = findViewById(R.id.txtHorario);
            imgClima = findViewById(R.id.imgClima);
            lnTudo = findViewById(R.id.lnTudo);
            swpDetalhes = findViewById(R.id.swpDetalhes);
        }

        public TextView getTxtCidade() {
            return txtCidade;
        }

        public TextView getTxtClima() {
            return txtClima;
        }

        public TextView getTxtTemperatura() {
            return txtTemperatura;
        }

        public TextView getTxtMaxMin() {
            return txtMaxMin;
        }

        public TextView getTxtHorario() {
            return txtHorario;
        }

        public ImageView getImgClima() {
            return imgClima;
        }

        public LinearLayout getLnTudo() {
            return lnTudo;
        }

        public SwipeRefreshLayout getSwpDetalhes() {
            return swpDetalhes;
        }
    }

    class ClimaAssincrono extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(holder != null){
                holder.getLnTudo().setVisibility(View.GONE);
                holder.getSwpDetalhes().setRefreshing(true);
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Log.d("DETALHESACTIVITY", "MSG = " + s);
            try
            {
                if(!s.isEmpty())
                {
                    Gson gson = new Gson();
                    OpenWeatherJson clima = gson.fromJson(s, OpenWeatherJson.class);
                    holder.getLnTudo().setVisibility(View.VISIBLE);
                    holder.getTxtCidade().setText(clima.getNome());
                    holder.getTxtClima().setText(clima.getClima().get(0).getDescricao());
                    holder.getTxtTemperatura().setText(String.format(locale, "%.1f%s", clima.getMain().getTemperatura(), getString(R.string.graus_celsius)));
                    holder.getTxtMaxMin().setText(String.format(locale, "%.1f%s / %.1f%s", clima.getMain().getTempMaxima(), getString(R.string.graus_celsius), clima.getMain().getTempMinima(), getString(R.string.graus_celsius)));
                    holder.getSwpDetalhes().setRefreshing(false);
                    holder.getImgClima().setImageDrawable(DetalhesActivity.this.getResources().getDrawable(Imagens.valueOf("i" + clima.getClima().get(0).getIcone()).getImagem()));
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", locale);
                    holder.getTxtHorario().setText(sdf.format(Calendar.getInstance().getTime()));
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
