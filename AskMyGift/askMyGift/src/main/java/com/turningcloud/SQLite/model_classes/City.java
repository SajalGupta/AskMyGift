package com.turningcloud.SQLite.model_classes;

import java.sql.Timestamp;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class City {
    public int cityId;
    public String city;
    public int countryId;
    Timestamp updationTime;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Timestamp getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Timestamp updationTime) {
        this.updationTime = updationTime;
    }


}
