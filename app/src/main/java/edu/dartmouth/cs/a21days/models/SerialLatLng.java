package edu.dartmouth.cs.a21days.models;

/**
 * Created by aenayet on 3/5/17.
 */

/**
 * Serializable version of Location class for Firebase
 */
public class SerialLatLng {
    public SerialLatLng() { }

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
