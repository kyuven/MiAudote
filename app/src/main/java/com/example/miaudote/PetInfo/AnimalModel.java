package com.example.miaudote.PetInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnimalModel {

    String id, nomeAnimal, idadeAnimal, descAnimal;

    public AnimalModel(String nomeAnimal, String idadeAnimal, String descAnimal) {
        this.nomeAnimal = nomeAnimal;
        this.idadeAnimal = idadeAnimal;
        this.descAnimal = descAnimal;
    }

    public AnimalModel() {

    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getIdadeAnimal() {
        return idadeAnimal;
    }

    public void setIdadeAnimal(String idadeAnimal) {
        this.idadeAnimal = idadeAnimal;
    }

    public String getDescAnimal() {
        return descAnimal;
    }

    public void setDescAnimal(String descAnimal) {
        this.descAnimal = descAnimal;
    }
}
