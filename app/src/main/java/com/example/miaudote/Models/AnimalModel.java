package com.example.miaudote.Models;

public class AnimalModel {

    String animalId, imgAnimal, tipoAnimal, nomeAnimal, descAnimal, ufAnimal,
            cidadeAnimal, bairroAnimal, lograAnimal, latAnimal, lngAnimal, userId;

    public AnimalModel() {

    }

    public AnimalModel(String animalId, String imgAnimal, String tipoAnimal, String nomeAnimal, String descAnimal,String ufAnimal,
                       String cidadeAnimal, String bairroAnimal, String lograAnimal, String latAnimal, String lngAnimal, String userId) {
        this.animalId = animalId;
        this.imgAnimal = imgAnimal;
        this.tipoAnimal = tipoAnimal;
        this.nomeAnimal = nomeAnimal;
        this.descAnimal = descAnimal;
        this.ufAnimal = ufAnimal;
        this.cidadeAnimal = cidadeAnimal;
        this.bairroAnimal = bairroAnimal;
        this.lograAnimal = lograAnimal;
        this.latAnimal = latAnimal;
        this.lngAnimal = lngAnimal;
        this.userId = userId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getImgAnimal() {
        return imgAnimal;
    }

    public void setImgAnimal(String imgAnimal) {
        this.imgAnimal = imgAnimal;
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

    public String getUfAnimal() {
        return ufAnimal;
    }

    public void setUfAnimal(String ufAnimal) {
        this.ufAnimal = ufAnimal;
    }

    public String getCidadeAnimal() {
        return cidadeAnimal;
    }

    public void setCidadeAnimal(String cidadeAnimal) {
        this.cidadeAnimal = cidadeAnimal;
    }

    public String getBairroAnimal() {
        return bairroAnimal;
    }

    public void setBairroAnimal(String bairroAnimal) {
        this.bairroAnimal = bairroAnimal;
    }

    public String getLograAnimal() {
        return lograAnimal;
    }

    public void setLograAnimal(String lograAnimal) {
        this.lograAnimal = lograAnimal;
    }

    public String getLatAnimal() {
        return latAnimal;
    }

    public void setLatAnimal(String latAnimal) {
        this.latAnimal = latAnimal;
    }

    public String getLngAnimal() {
        return lngAnimal;
    }

    public void setLngAnimal(String lngAnimal) {
        this.lngAnimal = lngAnimal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
