package com.turningcloud.askmygift.addgift;

import android.graphics.Bitmap;
import android.widget.TextView;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sajal on 12-08-2015.
 */
public class ProductData {
    URL imgUrl;
    String productID;
    String productName;
    String productDiscount;
    URL productURL;
    Bitmap productImageBit;

    public Bitmap getProductImageBit() {
        return productImageBit;
    }

    public void setProductImageBit(Bitmap productImageBit) {
        this.productImageBit = productImageBit;
    }

    public URL getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(URL imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public URL getProductURL() {
        return productURL;
    }

    public void setProductURL(URL productURL) {
        this.productURL = productURL;
    }
}
