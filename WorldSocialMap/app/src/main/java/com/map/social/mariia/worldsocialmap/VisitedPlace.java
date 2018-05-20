package com.map.social.mariia.worldsocialmap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mariia on 03.04.2018.
 */

public class VisitedPlace {
    private String pathToFile = "not added";
    private String visitedCountry;
    private String visitedCity;
    private String place;
    private String comment;

    public VisitedPlace() {
    }

    public VisitedPlace(String visitedCountry,
                        String visitedCity,
                        String place,
                        String comment) {
        this.visitedCountry = visitedCountry;
        this.visitedCity = visitedCity;
        this.place = place;
        this.comment = comment;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getVisitedCountry() {
        return visitedCountry;
    }

    public void setVisitedCountry(String visitedCountry) {
        this.visitedCountry = visitedCountry;
    }

    public String getVisitedCity() {
        return visitedCity;
    }

    public void setVisitedCity(String visitedCity) {
        this.visitedCity = visitedCity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
