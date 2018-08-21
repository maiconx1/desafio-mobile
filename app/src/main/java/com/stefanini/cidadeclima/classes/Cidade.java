package com.stefanini.cidadeclima.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
public class Cidade {
    @SerializedName("id")
    private int id;

    @SerializedName("coord")
    private ArrayList<String> coordenadas;

    @SerializedName("country")
    private String pais;

    @SerializedName("name")
    private String nome;

    @SerializedName("zoom")
    private int zoom;

    public Cidade() {
        this(0, new ArrayList<String>(), "", "", 0);
    }

    public Cidade(int id, ArrayList<String> coordenadas, String pais, String nome, int zoom) {
        this.id = id;
        this.coordenadas = coordenadas;
        this.pais = pais;
        this.nome = nome;
        this.zoom = zoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
