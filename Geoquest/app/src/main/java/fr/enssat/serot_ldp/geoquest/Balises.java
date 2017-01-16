package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Balises {
    private Coordonnee coordonnee;
    private Indice indice;

    public Balises (Coordonnee C, Indice I){
        this.coordonnee = C;
        this.indice = I;
    }

    public Indice getIndice() {
        return indice;
    }

    public void setIndice(Indice indice) {
        indice = indice;
    }

    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Coordonnee coordonnee) {
        coordonnee = coordonnee;
    }

}
