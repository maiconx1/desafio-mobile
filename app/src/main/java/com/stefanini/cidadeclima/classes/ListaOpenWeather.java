package com.stefanini.cidadeclima.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 21/08/2018.
 */
public class ListaOpenWeather {
    @SerializedName("list")
    private ArrayList<OpenWeatherJson> lista;

    public ArrayList<OpenWeatherJson> getLista() {
        return lista;
    }
}
