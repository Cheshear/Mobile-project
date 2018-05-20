package com.map.social.mariia.worldsocialmap;

/**
 * Created by mariia on 03.04.2018.
 */

public class WantPlace {
    private String wishCountry;
    private String wishCity;
    private String place;

    public WantPlace() {
    }

    public WantPlace(String visitedCountry, String visitedCity, String place) {
        this.wishCountry = visitedCountry;
        this.wishCity = visitedCity;
        this.place = place;
    }

    public String getWishCountry() {
        return wishCountry;
    }

    public void setWishCountry(String wishCountry) {
        this.wishCountry = wishCountry;
    }

    public String getWishCity() {
        return wishCity;
    }

    public void setWishCity(String wishCity) {
        this.wishCity = wishCity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
