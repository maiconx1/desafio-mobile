package com.stefanini.cidadeclima.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stefanini.cidadeclima.R;
import com.stefanini.cidadeclima.activities.DetalhesActivity;
import com.stefanini.cidadeclima.classes.Cidade;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
public class AdapterCidades extends RecyclerView.Adapter<AdapterCidades.Holder> {
    private ArrayList<Cidade> cidades;
    private Activity activity;

    public AdapterCidades(ArrayList<Cidade> cidades, Activity activity) {
        this.cidades = cidades;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_list_cidade, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Cidade cidade = cidades.get(position);
        holder.getLnTudo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent i = new Intent(activity, DetalhesActivity.class);
                i.putExtra("CIDADE", gson.toJson(cidade));
                activity.startActivity(i);
            }
        });
        holder.getTxtCidade().setText(cidade.getNome());
    }

    @Override
    public long getItemId(int i) {
        return cidades.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return cidades.size();
    }

    public void addCidade(Cidade cidade) {
        cidades.add(cidade);
        notifyItemInserted(getItemCount());
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView txtCidade;
        private CardView lnTudo;

        Holder(View view) {
            super(view);
            txtCidade = view.findViewById(R.id.txtCidade);
            lnTudo = view.findViewById(R.id.lnTudo);
        }

        TextView getTxtCidade() {
            return txtCidade;
        }

        CardView getLnTudo() {
            return lnTudo;
        }
    }
}
