package com.turningcloud.SQLite.model_classes;

import java.sql.Timestamp;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class Diary {

    public int diaryId;
    public  String userId;
    public String diaryName;
    public String diaryDesc;
    public String diaryType;
    public Timestamp creationTime;
    public Timestamp updationTime;

    public int getDiaryId() {
        return diaryId;
    }

    public String getDiaryDesc() {
        return diaryDesc;
    }

    public void setDiaryDesc(String diaryDesc) {
        this.diaryDesc = diaryDesc;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiaryName() {
        return diaryName;
    }

    public void setDiaryName(String diaryName) {
        this.diaryName = diaryName;
    }

    public String getDiaryType() {
        return diaryType;
    }

    public void setDiaryType(String diaryType) {
        this.diaryType = diaryType;
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
