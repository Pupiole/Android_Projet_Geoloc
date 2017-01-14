package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Indice {
    String image;
    String text;

    public Indice (String text, String image){

        this.image = image;
        this.text = text;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
