package com.stefanini.cidadeclima.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
public class CidadesList {
    @SerializedName("data")
    private ArrayList<Cidade> cidades;

    public ArrayList<Cidade> getCidades() {
        return cidades;
    }
}
