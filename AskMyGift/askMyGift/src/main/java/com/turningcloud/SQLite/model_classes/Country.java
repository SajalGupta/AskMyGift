package com.turningcloud.SQLite.model_classes;

import java.sql.Timestamp;
import java.sql.Timestamp;

/**
 * Created by MAHIRAJ on 8/11/2015.
 */
public class Country {
    public int counrtyId;
    public String countryCode;
    public String country;
    public Timestamp updationTime;

    public int getCounrtyId() {
        return counrtyId;
    }

    public void setCounrtyId(int counrtyId) {
        this.counrtyId = counrtyId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Timestamp getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Timestamp updationTime) {
        this.updationTime = updationTime;
    }


}
