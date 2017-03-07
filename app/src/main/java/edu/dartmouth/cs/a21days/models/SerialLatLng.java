package edu.dartmouth.cs.a21days.models;

/**
 * Serializable version of Location class for Firebase.
 */
public class SerialLatLng {

    private Double latitude;
    private Double longitude;

    // constructor
    public SerialLatLng() {
        // empty
    }

    // get latitude
    public Double getLatitude() {
        return latitude;
    }

    // set latitude
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    // get longitude
    public Double getLongitude() {
        return longitude;
    }

    // set longitude
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
