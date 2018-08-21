package com.stefanini.cidadeclima.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.activities.DetalhesActivity;
import com.stefanini.cidadeclima.classes.Favorito;
import com.stefanini.cidadeclima.classes.Singleton;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
public class AdapterFavoritos extends RecyclerView.Adapter<AdapterFavoritos.Holder> {
    private ArrayList<Favorito> favoritos;
    private Activity activity;

    public AdapterFavoritos(ArrayList<Favorito> favoritos, Activity activity) {
        this.favoritos = favoritos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_list_favorito, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Favorito favorito = favoritos.get(position);
        holder.getLnTudo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent i = new Intent(activity, DetalhesActivity.class);
                i.putExtra("ESPORTE", gson.toJson(favorito));
                activity.startActivity(i);
            }
        });
        holder.getTxtCidade().setText(favorito.getNome());
        holder.getTxtClima().setText(favorito.getClima());
        holder.getTxtTemperatura().setText(String.format(activity.getString(R.string.temperatura), favorito.getTemperatura()));
    }

    @Override
    public long getItemId(int i) {
        return favoritos.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    public void addEsporte(Favorito favorito) {
        favoritos.add(favorito);
        notifyItemInserted(getItemCount());
    }

    public void setFavoritos(ArrayList<Favorito> favoritos) {
        this.favoritos = favoritos;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView txtCidade, txtClima, txtTemperatura;
        private LinearLayout lnTudo;

        Holder(View view) {
            super(view);
            txtCidade = view.findViewById(R.id.txtCidade);
            txtClima = view.findViewById(R.id.txtClima);
            txtTemperatura = view.findViewById(R.id.txtTemperatura);
            lnTudo = view.findViewById(R.id.lnTudo);
        }

        TextView getTxtCidade() {
            return txtCidade;
        }

        public TextView getTxtClima() {
            return txtClima;
        }

        public TextView getTxtTemperatura() {
            return txtTemperatura;
        }

        LinearLayout getLnTudo() {
            return lnTudo;
        }
    }
}
