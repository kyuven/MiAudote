package com.example.miaudote.Models;

public class OngModel {

    public OngModel() {

    }

    public OngModel(String nomeOng, String descOng, String cidadeOng,
                    String bairroOng, String lograOng, String telOng, String instaOng,
                    String emailOng, String twitterOng, String faceOng) {
        this.nomeOng = nomeOng;
        this.descOng = descOng;
        this.cidadeOng = cidadeOng;
        this.bairroOng = bairroOng;
        this.lograOng = lograOng;
        this.telOng = telOng;
        this.instaOng = instaOng;
        this.emailOng = emailOng;
        this.twitterOng = twitterOng;
        this.faceOng = faceOng;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }

    public String getDescOng() {
        return descOng;
    }

    public void setDescOng(String descOng) {
        this.descOng = descOng;
    }

    public String getCidadeOng() {
        return cidadeOng;
    }

    public void setCidadeOng(String cidadeOng) {
        this.cidadeOng = cidadeOng;
    }

    public String getBairroOng() {
        return bairroOng;
    }

    public void setBairroOng(String bairroOng) {
        this.bairroOng = bairroOng;
    }

    public String getLograOng() {
        return lograOng;
    }

    public void setLograOng(String lograOng) {
        this.lograOng = lograOng;
    }

    public String getTelOng() {
        return telOng;
    }

    public void setTelOng(String telOng) {
        this.telOng = telOng;
    }

    public String getInstaOng() {
        return instaOng;
    }

    public void setInstaOng(String instaOng) {
        this.instaOng = instaOng;
    }

    public String getEmailOng() {
        return emailOng;
    }

    public void setEmailOng(String emailOng) {
        this.emailOng = emailOng;
    }

    public String getTwitterOng() {
        return twitterOng;
    }

    public void setTwitterOng(String twitterOng) {
        this.twitterOng = twitterOng;
    }

    public String getFaceOng() {
        return faceOng;
    }

    public void setFaceOng(String faceOng) {
        this.faceOng = faceOng;
    }

    String nomeOng, descOng, cidadeOng, bairroOng, lograOng,
        telOng, instaOng, emailOng, twitterOng, faceOng; // add imagem

}
