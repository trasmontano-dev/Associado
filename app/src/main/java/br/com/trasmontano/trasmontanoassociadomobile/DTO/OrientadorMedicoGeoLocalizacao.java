package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import java.io.Serializable;

/**
 * Created by mmaganha on 24/08/2016.
 */
public class OrientadorMedicoGeoLocalizacao implements Serializable {
    private String Latitude;
    private String Longitude;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
