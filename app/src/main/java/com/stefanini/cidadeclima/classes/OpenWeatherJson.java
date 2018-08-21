package com.stefanini.cidadeclima.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
/*
{
"sys":{"type":1,"id":4472,"message":0.0186,"country":"BR","sunrise":1534756386,"sunset":1534797895},
"id":3470127,"name":"Belo Horizonte","cod":200}
 */
public class OpenWeatherJson {
    @SerializedName("weather")
    private ArrayList<Clima> clima;

    @SerializedName("main")
    private Main main;

    @SerializedName("name")
    private String nome;

    public ArrayList<Clima> getClima() {
        return clima;
    }

    public void setClima(ArrayList<Clima> clima) {
        this.clima = clima;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public class Main {
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

    public class Clima {
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
