package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Indice {
    String image;
    String texte;

    public Indice (String text, String image){

        this.image = image;
        this.texte = text;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTexte() {
        return texte;
    }

    public void setText(String text) {
        this.texte = text;
    }
}
