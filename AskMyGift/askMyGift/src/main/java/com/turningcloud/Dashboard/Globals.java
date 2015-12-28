package com.turningcloud.Dashboard;

import android.content.Context;

import com.turningcloud.askmygift.friendlist.FriendData;
import com.turningcloud.askmygift.addgift.ProductData;

import java.util.ArrayList;

/**
 * Created by sumeet on 27/7/15.
 */
public class Globals {
    private static ArrayList<String> globalWishList;
    private static Context globalContext;
    private static ArrayList<ProductData> wishlistData = new ArrayList<>();
    private static ArrayList<FriendData> friendList = new ArrayList<>();
    private static Boolean dataAvailable = new Boolean(false);

    public static Boolean getDataAvailable() {
        return dataAvailable;
    }

    public static void setDataAvailable(Boolean dataAvailable) {
        Globals.dataAvailable = dataAvailable;
    }

    public static ArrayList<FriendData> getFriendList() {
        return friendList;
    }

    public static void setFriendList(ArrayList<FriendData> friendList) {
        Globals.friendList = friendList;
    }

    public static ArrayList<ProductData> getWishlistData() {
        return wishlistData;
    }

    public static void setWishlistData(ArrayList<ProductData> wishlistData) {
        Globals.wishlistData = wishlistData;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }

    public static void setGlobalContext(Context globalContext) {
        Globals.globalContext = globalContext;
    }

    public static ArrayList<String> getGlobalWishList() {
        return globalWishList;
    }

    public static void setGlobalWishList(ArrayList<String> globalWishList) {
        Globals.globalWishList = globalWishList;
    }
}
