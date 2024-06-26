package com.example.miaudote;

public class DataClassAdopt {
    public String getNomeAnimalAdpt() {
        return nomeAnimalAdpt;
    }

    public void setNomeAnimalAdpt(String nomeAnimalAdpt) {
        this.nomeAnimalAdpt = nomeAnimalAdpt;
    }

    public String getDescAnimalAdpt() {
        return descAnimalAdpt;
    }

    public void setDescAnimalAdpt(String descAnimalAdpt) {
        this.descAnimalAdpt = descAnimalAdpt;
    }

    public String getImgAnimalAdpt() {
        return imgAnimalAdpt;
    }

    public void setImgAnimalAdpt(String imgAnimalAdpt) {
        this.imgAnimalAdpt = imgAnimalAdpt;
    }

    public DataClassAdopt(String nomeAnimalAdpt, String descAnimalAdpt, String imgAnimalAdpt) {
        this.nomeAnimalAdpt = nomeAnimalAdpt;
        this.descAnimalAdpt = descAnimalAdpt;
        this.imgAnimalAdpt = imgAnimalAdpt;
    }

    private String nomeAnimalAdpt, descAnimalAdpt, imgAnimalAdpt;
}
