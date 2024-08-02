package com.example.miaudote.Models;

public class AnimalModel {

    String id, tipoAnimal, nomeAnimal, descAnimal;

    public AnimalModel(String id, String tipoAnimal, String nomeAnimal, String descAnimal) {
        this.id = id;
        this.tipoAnimal = tipoAnimal;
        this.nomeAnimal = nomeAnimal;
        this.descAnimal = descAnimal;
    }

    public AnimalModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getDescAnimal() {
        return descAnimal;
    }

    public void setDescAnimal(String descAnimal) {
        this.descAnimal = descAnimal;
    }
}
