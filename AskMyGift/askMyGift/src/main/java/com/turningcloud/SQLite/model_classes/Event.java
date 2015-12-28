package com.turningcloud.SQLite.model_classes;

import java.sql.Timestamp;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class Event {
    public int eventId;
    public int diaryId;
    public  String eventName;
    public  String eventDesc;
    public  String eventStartDate;
    public  String eventEndDate;
    public Timestamp creationTime;
    public Timestamp updationTime;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Timestamp updationTime) {
        this.updationTime = updationTime;
    }
}
