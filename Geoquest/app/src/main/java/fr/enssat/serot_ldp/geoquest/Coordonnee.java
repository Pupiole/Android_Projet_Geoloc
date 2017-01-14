package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Coordonnee {

    public double longitude;
    public double latitude;

    public void Coordonee (double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
