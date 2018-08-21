package com.stefanini.cidadeclima.classes;

/**
 * Criado por Maicon Dias Castro em 20/08/2018.
 */
public class Favorito {
    private int id;
    private String nome, clima;
    private double temperatura;

    public Favorito() {
        this(0, "", "", 0);
    }

    public Favorito(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Favorito(int id, String nome, String clima, double temperatura) {
        this.id = id;
        this.nome = nome;
        this.clima = clima;
        this.temperatura = temperatura;
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

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
}
