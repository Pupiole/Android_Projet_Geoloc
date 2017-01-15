package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Balises {

    public Coordonnee C;

    public Indice I;

    public Balises (Coordonnee C, Indice I){
        this.C = C;
        this.I = I;
    }

    public Indice getI() {
        return I;
    }

    public void setI(Indice i) {
        I = i;
    }

    public Coordonnee getC() {
        return C;
    }

    public void setC(Coordonnee c) {
        C = c;
    }

}
