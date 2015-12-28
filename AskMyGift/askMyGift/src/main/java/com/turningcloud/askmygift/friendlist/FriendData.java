package com.turningcloud.askmygift.friendlist;

import android.graphics.Bitmap;

/**
 * Created by Sajal on 13-08-2015.
 */
public class FriendData {
    String facebookID;
    String friendName;
    Bitmap friendImage;

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Bitmap getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(Bitmap friendImage) {
        this.friendImage = friendImage;
    }
}
