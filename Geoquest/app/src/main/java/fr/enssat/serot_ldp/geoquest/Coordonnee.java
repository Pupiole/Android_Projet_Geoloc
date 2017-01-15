package fr.enssat.serot_ldp.geoquest;

/**
 * Created by paul on 15/12/16.
 */

public class Coordonnee {

    public double Longitude;
    public double Latitude;

    public Coordonnee (double Longitude, double Latitude){
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }


    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }


}
