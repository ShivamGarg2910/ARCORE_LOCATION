package com.example.setlocalposition;

public class Result {

    String name;
    Double lat,lon,dis;

    public Result(String name, Double lat, Double lon,Double dis) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.dis = dis;
    }

    public Result() {
    }

    public Double getDis() {
        return dis;
    }

    public void setDis(Double dis) {
        this.dis = dis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
