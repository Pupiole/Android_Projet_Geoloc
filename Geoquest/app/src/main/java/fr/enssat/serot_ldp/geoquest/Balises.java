package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Balises {
    private Coordonnee C;
    private Indice I;

    public void Balises (Coordonnee C, Indice I){
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
