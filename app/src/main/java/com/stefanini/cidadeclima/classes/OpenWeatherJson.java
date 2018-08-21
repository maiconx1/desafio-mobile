package com.stefanini.cidadeclima.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
/*
{
"main":{"temp":20.35,"pressure":1025,"humidity":42,"temp_min":20,"temp_max":21},
"visibility":10000,"wind":{"speed":3.6,"deg":60},"clouds":{"all":0},"dt":1534806000,
"sys":{"type":1,"id":4472,"message":0.0186,"country":"BR","sunrise":1534756386,"sunset":1534797895},
"id":3470127,"name":"Belo Horizonte","cod":200}
 */
public class OpenWeatherJson {
    @SerializedName("weather")
    private ArrayList<Clima> clima;

    @SerializedName("main")
    private ArrayList<Main> main;

    public ArrayList<Clima> getClima() {
        return clima;
    }

    public void setClima(ArrayList<Clima> clima) {
        this.clima = clima;
    }

    public ArrayList<Main> getMain() {
        return main;
    }

    public void setMain(ArrayList<Main> main) {
        this.main = main;
    }

    private class Main {
        @SerializedName("temp")
        private double temperatura;

        @SerializedName("temp_min")
        private double tempMinima;

        @SerializedName("temp_max")
        private double tempMaxima;

        public double getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(double temperatura) {
            this.temperatura = temperatura;
        }

        public double getTempMinima() {
            return tempMinima;
        }

        public void setTempMinima(double tempMinima) {
            this.tempMinima = tempMinima;
        }

        public double getTempMaxima() {
            return tempMaxima;
        }

        public void setTempMaxima(double tempMaxima) {
            this.tempMaxima = tempMaxima;
        }
    }

    private class Clima {
        @SerializedName("id")
        private int id;

        @SerializedName("description")
        private String descricao;

        @SerializedName("icon")
        private String icone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getIcone() {
            return icone;
        }

        public void setIcone(String icone) {
            this.icone = icone;
        }
    }
}
