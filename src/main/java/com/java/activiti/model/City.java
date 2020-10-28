package com.java.activiti.model;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("cityID")
    private int cityID;

    @Field("cityName")
    private String cityName;

    @Field("longitude")
    private String longitude;

    @Field("Latitude")
    private String Latitude;

    @Field("cityPinYin")
    private String cityPinYin;

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getCityPinYin() {
        return cityPinYin;
    }

    public void setCityPinYin(String cityPinYin) {
        this.cityPinYin = cityPinYin;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityID=" + cityID +
                ", cityName='" + cityName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", cityPinYin='" + cityPinYin + '\'' +
                '}';
    }
}
