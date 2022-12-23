package com.example.hw1.Models;

public class UserItems {

    private String name;
    private int score;
    private double lat;
    private double lon;

    public UserItems(String name, int score, double lat, double lon) {
        this.name = name;
        this.score = score;
        this.lat = lat;
        this.lon = lon;
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
