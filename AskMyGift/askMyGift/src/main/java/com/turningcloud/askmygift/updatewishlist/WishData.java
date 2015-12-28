package com.turningcloud.askmygift.updatewishlist;

import android.graphics.Bitmap;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sajal on 11-08-2015.
 */
public class WishData {

    String wishName;
    Bitmap wishImage;

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public Bitmap getWishImage() {
        return wishImage;
    }

    public void setWishImage(Bitmap wishImage) {
        this.wishImage = wishImage;
    }
}
