package com.example.hw1.Models;

public class UserItems {

    private String name = "";
    private int score = 0;
    private double lat = 0.0;
    private double lon = 0.0;

    public UserItems() {

    }

    public String getName() {
        return name;
    }

    public UserItems setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public UserItems setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public UserItems setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public UserItems setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
